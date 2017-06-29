package com.themagzuz.discord.discordmmo.datatypes;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.themagzuz.discord.discordmmo.datatypes.actions.IdleAction;

import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

public class Player {
	
	public String name;
	
	protected Action currentAction;
	
	public Inventory inventory;
	
	public User user;
	public PrivateChannel privateChannel;
	
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	Future<?> future;
	
	////////// USER PREFERENCES //////////
	public boolean mention = true;
	public boolean messageOnFinish = true;

	
	////////// CONSTRUCTORS //////////
	
	/**
	 * You probably want to use {@link com.themagzuz.discord.discordmmo.PlayerHandler#CreatePlayer(User, String) CreatePlayer}
	 * @param _name
	 * @param _user
	 */
	public Player(String _name, User _user)
	{
		name = _name;
		user = _user;
		user.openPrivateChannel().queue(new Consumer<PrivateChannel>()
				{
					@Override
					public void accept(PrivateChannel c)
					{
						privateChannel = c;
					}
				});
		Idle();
	}
	
	 ////////// METHODS //////////
	
	/**
	 * Makes the player start an action, if they're idle
	 * @param action The action to start
	 * @return True if the action was started, false otherwise
	 */
	public boolean StartAction(Action action)
	{
		if (currentAction == null || currentAction instanceof IdleAction)
		{
			future.cancel(true);
			currentAction = action;
			future = executor.scheduleAtFixedRate(action, 0, 1, TimeUnit.SECONDS);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void Idle()
	{
		if (currentAction == null)
		{
			currentAction = new IdleAction(this);
			future = executor.scheduleAtFixedRate(currentAction, 0, 1, TimeUnit.SECONDS);
		}
		else
		{
			future.cancel(true);
			currentAction = new IdleAction(this);
			future = executor.scheduleAtFixedRate(currentAction, 0, 1, TimeUnit.SECONDS);
		}
	}
	
	////////// GETTERS AND SETTERS //////////
	
	public Action getCurrentAction()
	{
		return currentAction;
	}
	
}

