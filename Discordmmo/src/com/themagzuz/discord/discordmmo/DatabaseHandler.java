package com.themagzuz.discord.discordmmo;

import com.mysql.jdbc.Driver;
import com.themagzuz.discord.discordmmo.datatypes.Action;
import com.themagzuz.discord.discordmmo.datatypes.Player;
import com.themagzuz.discord.discordmmo.datatypes.Stack;
import com.themagzuz.util.configload.Config;
import net.dv8tion.jda.core.entities.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by TheMagzuz on 31-07-2017.
 *
 * @author TheMagzuz
 */
public final class DatabaseHandler
{

    private static final String USERNAME;
    private static final String PASSWORD;

    private static final String URL = "jdbc:mysql://localhost/discord_mmo";

    private static volatile Connection conn;

    //region Prepared get statements
    private static PreparedStatement getPlayerFromMention;
    private static PreparedStatement getPlayerCountWithMention;
    private static PreparedStatement getInventoryFromMention;
    private static PreparedStatement getCurrentActionFromMention;
    private static PreparedStatement getPlayerPrefsFromMention;
    //endregion

    //region Prepared set statements
    private static NamedParamStatement setPlayer;
    private static PreparedStatement setInventory;
    //endregion

    static
    {
        Config.SetConfigFile(new File("config.cfg").getAbsolutePath());
        USERNAME = Config.GetString("username");
        PASSWORD = Config.GetString("password");

        Statement stmt = null;
        try
        {
            Driver driver = new Driver();

            System.out.println("[SQLHandler] Registering driver");
            DriverManager.registerDriver(driver);

            System.out.println("[SQLHandler] Connecting to server");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //region Initialize prepared get statements
            getPlayerFromMention = conn.prepareStatement("SELECT * FROM users WHERE mention = ?");
            getPlayerCountWithMention = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE mention = ?");
            getInventoryFromMention = conn.prepareStatement("SELECT * FROM inventories WHERE ownermention = ?");
            getCurrentActionFromMention = conn.prepareStatement("SELECT currentActionName, currentActionFinishTime FROM users WHERE mention = ?");
            getPlayerPrefsFromMention = conn.prepareStatement("SELECT preference_mention, preference_pm FROM users WHERE mention = ?");
            //endregion

            //region Initialize prepared set statements
            setPlayer = new NamedParamStatement(conn, "INSERT INTO users (name, mention, currentActionName, currentActionFinishTime, preference_mention, preference_pm) " +
                    "VALUES(:name , :mention , :actionName , :actionTime , :pref_mention , :pref_pm ) " +
                    "ON DUPLICATE KEY UPDATE name=VALUES(name), mention=VALUES(mention) , currentActionName=VALUES(currentActionName) , currentActionFinishTime=VALUES(currentActionFinishTime) " +
                    ", preference_mention=VALUES(preference_mention) , preference_pm=VALUES(preference_pm) ");
            setInventory = conn.prepareStatement("INSERT INTO inventories (ownermention, slot, itemid, itemcount) " +
                    "VALUES(?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE ownermention=VALUES(ownermention), slot=VALUES(slot), itemid=VALUES(itemid), itemcount=VALUES(itemcount)");
            //endregion

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Gets the player from the database and adds them to the list of players
     * @param user The user that owns the player
     * @return The player that is now accessible
     */
    public static Player FetchAndCreatePlayer(User user)
    {
        if (PlayerHandler.HasPlayer(user))
            return PlayerHandler.GetPlayer(user);
        if (!CanFetchPlayer(user))
            return null;

        try
        {
            //region Setup player
            getPlayerFromMention.setString(1, user.getAsMention());

            ResultSet rs = getPlayerFromMention.executeQuery();

            if (!rs.next())
                return null;

            Player player = PlayerHandler.CreatePlayer(user, rs.getString("name"));
            //endregion

            //region Setup player inventory
            getInventoryFromMention.setString(1, user.getAsMention());

            rs = getInventoryFromMention.executeQuery();

            while (rs.next())
            {
                int slot = rs.getInt("slot");
                int count = rs.getInt("itemcount");
                int itemId = rs.getInt("itemid");

                if (itemId != -1 && count > 0)
                    player.inventory.stacks[slot] = ItemHandler.getItem(itemId).CreateStack(count);
            }
            //endregion

            //region Setup current action
            getCurrentActionFromMention.setString(1, user.getAsMention());

            rs = getCurrentActionFromMention.executeQuery();

            if (!rs.next())
                return null;

            Action a = Action.GetActionFromName(rs.getString("currentActionName"), player, false);

            a.finishTime = rs.getTimestamp("currentActionFinishTime").toInstant();


            player.StartAction(a, true);
            //endregion

            //region Setup player prefs
            getPlayerPrefsFromMention.setString(1, user.getAsMention());

            rs = getPlayerPrefsFromMention.executeQuery();

            if (!rs.next())
                return null;

            player.mention = rs.getBoolean("preference_mention");
            player.sendPrivateMessages = rs.getBoolean("preference_pm");

            //endregion

            player.Tick();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean CanFetchPlayer(User user)
    {
        return CanFetchPlayer(user.getAsMention());
    }

    public static boolean CanFetchPlayer(String mention)
    {
        if (PlayerHandler.HasPlayer(mention))
            return false;
        try
        {
            getPlayerCountWithMention.setString(1, mention);

            ResultSet rs = getPlayerCountWithMention.executeQuery();

            String counter;

            return rs.next();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static void Save(Player p)
    {
        try
        {
            //region Set player
            setPlayer.setString("name", p.name);
            setPlayer.setString("mention", p.user.getAsMention());
            if (p.getCurrentAction() != null)
            {
                setPlayer.setString("actionName", p.getCurrentAction().GetName());
            }
            else
            {
                setPlayer.setString("actionName", "idle");
            }
            if (p.getCurrentAction() != null)
            {
                setPlayer.setTimestamp("actionTime", Timestamp.from(p.getCurrentAction().finishTime));
            }
            else
            {
                setPlayer.setTimestamp("actionTime", Timestamp.from(Instant.MAX));
            }
            setPlayer.setBoolean("pref_mention", p.mention);
            setPlayer.setBoolean("pref_pm", p.sendPrivateMessages);
            setPlayer.execute();
            //endregion

            //region Set inventory
            setInventory.setString(1, p.user.getAsMention());

            for (int i = 0; i < p.inventory.stacks.length; i++)
            {

                Stack s = p.inventory.stacks[i];

                // Slot
                setInventory.setInt(2, i);

                if (s == null || s.item == null)
                {
                    // ID
                    setInventory.setInt(3, -1);
                    // Count
                    setInventory.setInt(4, 0);
                }
                else
                {
                    // ID
                    setInventory.setInt(3, s.item.GetId());
                    // Count
                    setInventory.setInt(4, s.size);
                }
                setInventory.execute();
            }

            //endregion

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void SaveAll()
    {
        long start = System.currentTimeMillis();
        System.out.println("[Database Handler] Starting saving all players");
        for (Player p : PlayerHandler.GetPlayers())
        {
            Save(p);
        }

        System.out.println("[Database Handler] Saving all players took " + (System.currentTimeMillis()-start) + "ms");

    }

}

class NamedParamStatement {
    public NamedParamStatement(Connection conn, String sql) throws SQLException {
        int pos;
        while((pos = sql.indexOf(":")) != -1) {
            int end = sql.substring(pos).indexOf(" ");
            if (end == -1)
                end = sql.length();
            else
                end += pos;
            fields.add(sql.substring(pos+1,end));
            sql = sql.substring(0, pos) + "?" + sql.substring(end);
        }
        prepStmt = conn.prepareStatement(sql);
    }

    public PreparedStatement getPreparedStatement() {
        return prepStmt;
    }
    public ResultSet executeQuery() throws SQLException {
        return prepStmt.executeQuery();
    }

    public boolean execute() throws SQLException
    {
        return prepStmt.execute();
    }

    public void close() throws SQLException {
        prepStmt.close();
    }

    public void setInt(String name, int value) throws SQLException {
        prepStmt.setInt(getIndex(name), value);
    }

    public void setString(String name, String value) throws SQLException
    {
        prepStmt.setString(getIndex(name), value);
    }

    public void setTimestamp(String name, Timestamp value) throws SQLException
    {
        prepStmt.setTimestamp(getIndex(name), value);
    }

    public void setBoolean(String name, boolean value) throws SQLException
    {
        prepStmt.setBoolean(getIndex(name), value);
    }


    private int getIndex(String name) {
        return fields.indexOf(name)+1;
    }
    private PreparedStatement prepStmt;
    private List<String> fields = new ArrayList<String>();
}
