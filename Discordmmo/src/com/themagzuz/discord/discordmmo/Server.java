package com.themagzuz.discord.discordmmo;

import java.sql.Time;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Created by TheMagzuz on 29-07-2017.
 *
 * @author TheMagzuz
 */
public class Server implements Runnable
{

    public Thread thread;

    private int ticksBetweenSave = 60;

    private int ticksSinceLastSave = 59;

    /**
     * The amount of ticks per second
     */
    private static double tickRate = 2;

    public static Server INSTANCE;

    static
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Server();
        }
    }

    public Server()
    {
        thread = new Thread(this);
    }

    @Override
    public void run()
    {

        while (true)
        {
            PlayerHandler.UpdatePlayers();
            if (ticksSinceLastSave >= ticksBetweenSave)
            {
                ticksSinceLastSave = 0;
                DatabaseHandler.SaveAll();
            }
            ticksSinceLastSave++;
            try
            {
               TimeUnit.MILLISECONDS.sleep((long) ((1/tickRate)*1000));
            }
            catch (InterruptedException e){}
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


}
