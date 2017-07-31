package com.themagzuz.discord.discordmmo.datatypes;

import java.lang.reflect.InvocationTargetException;

import com.themagzuz.discord.discordmmo.PlayerHandler;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public abstract class ActionCommand<T extends Action> extends Command
{

 	Class<T> clazz;
 	
	public ActionCommand(String _commandName, Class<T> _class)
	{
		super();
		clazz = _class;
		commandName = _commandName;
	}
	
	@Override
	public void run(User sender, String[] args, MessageChannel channel)
	{
		if (!PlayerHandler.RegisterOrLogin(sender))
		{
			channel.sendMessage(sender.getAsMention() + ": You are not registered. You can register with $register [name]").queue();
			return;
		}
		Player player = PlayerHandler.GetPlayer(sender);
		/*if (player.currentAction instanceof IdleAction)
		{*/
			try
			{
				player.StartAction(clazz.getDeclaredConstructor(Player.class, boolean.class).newInstance(player, player.sendPrivateMessages)
				);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				channel.sendMessage(sender.getAsMention() + ": Something went wrong! Please try again or submit a bug report here: https://github.com/DiscordMMO/DiscordMMO/issues").queue();
				e.printStackTrace();
			}
		/*}
		else
		{
			channel.sendMessage(sender.getAsMention() + ": You are already doing something. Please wait for that to finish before starting something new").queue();
		}
		*/
	}

	
}
