package com.themagzuz.discord.discordmmo.datatypes;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.themagzuz.discord.discordmmo.PlayerHandler;
import com.themagzuz.discord.discordmmo.datatypes.actions.IdleAction;

import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

public class Player {
	
	public String name;
	
	protected Action currentAction;
	
	public Inventory inventory;
	
	public User user;
	public PrivateChannel privateChannel;

    public final Integer id;

    //region User Preferences
	public boolean mention = true;
	public boolean sendPrivateMessages = true;
    //endregion

    //region Constructors
    /**
	 * You probably want to use {@link com.themagzuz.discord.discordmmo.PlayerHandler#CreatePlayer(User, String) CreatePlayer}
	 * @param _name
	 * @param _user
	 */
	public Player(String _name, User _user)
	{
        this(_name, _user, PlayerHandler.nextPlayerId);
        PlayerHandler.nextPlayerId++;
	}

	public Player(String _name, User _user, int _id)
    {
        id = _id;
        name = _name;
        user = _user;
        inventory = new Inventory();
        user.openPrivateChannel().queue(new Consumer<PrivateChannel>()
        {
            @Override
            public void accept(PrivateChannel c)
            {
                privateChannel = c;
                System.out.println("Created private channel");
            }
        });
        Idle(false);
    }

    //endregion

    //region Methods
    /**
	 * Makes the player start an action, if they're idle
	 * @param action The action to start
	 * @return True if the action was started, false otherwise
	 */
	public boolean StartAction(Action action)
	{
        return StartAction(action, false);
	}

    /**
     * Makes the player start an action, if they're idle
     * @param action The action to start
     * @return True if the action was started, false otherwise
     */
    public boolean StartAction(Action action, boolean forced)
    {
        if (!forced)
        {
            if ((currentAction == null || currentAction instanceof IdleAction))
            {
                currentAction = action;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            currentAction = action;
            return true;
        }
    }

    public void Idle(boolean announce)
    {
            currentAction = new IdleAction(this, announce);
    }

	public void Tick()
    {
        currentAction.run();
    }

    //endregion

    //region Getters and setters
    public Action getCurrentAction()
	{
		return currentAction;
	}
    //endregion
	
}

