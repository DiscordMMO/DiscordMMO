package com.themagzuz.discord.discordmmo.datatypes;

import com.themagzuz.discord.discordmmo.CommandHandler;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public abstract class Command
{
	/**
	 * This method is called when the command is run
	 * @param channel TODO
	 */
	public abstract void run(User sender, String[] args, MessageChannel channel);

	/**
	 * When making a command, there's no need to include the command prefix in the command name
	 */
	public String commandName;
	
	
	
	public Command()
	{
		CommandHandler.commands.add(this);
	}
	
	
}
