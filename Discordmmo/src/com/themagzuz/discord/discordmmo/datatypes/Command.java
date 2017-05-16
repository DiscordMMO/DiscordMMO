package com.themagzuz.discord.discordmmo.datatypes;

import com.themagzuz.discord.discordmmo.CommandHandler;

public abstract class Command
{
	/**
	 * This method is called when the command is run
	 */
	public abstract void run();

	/**
	 * When making a command, there's no need to include the command prefix in the command name
	 */
	public String commandName;
	
	
	
	public Command()
	{
		CommandHandler.commands.add(this);
	}
	
	
}
