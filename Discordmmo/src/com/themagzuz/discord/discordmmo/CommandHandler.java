package com.themagzuz.discord.discordmmo;

import java.util.ArrayList;
import java.util.List;

import com.themagzuz.discord.discordmmo.datatypes.Command;
import com.themagzuz.discord.discordmmo.datatypes.Inventory;
import com.themagzuz.discord.discordmmo.datatypes.commands.*;
import com.themagzuz.discord.discordmmo.datatypes.commands.action.ChopCommand;
import com.themagzuz.util.configload.Config;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Ignore this class for now, this is only a test! 
 */
public final class CommandHandler
{

	private CommandHandler() {}
	
	public static String commandPrefix;
	
	public static List<Command> commands = new ArrayList<Command>();
	
	public static void SetupCommands()
	{
		
		Config.SetConfigFile(System.getProperty("user.dir") + "\\botConfig.cfg");
		commandPrefix = Config.GetString("commandPrefix");
		
		new RegisterCommand();
		new StatusCommand();
		new PrefCommand();
		new ChopCommand();
		new InventoryCommand();
		new SaveCommand();
	}
	
	public static void HandleCommand(String message, User sender, MessageChannel channel)
	{
			String command;
			String[] args;
			if (message.contains(" "))
			{
				args = message.substring(message.indexOf(' ') + 1, message.length()).split(" ");
				command = message.substring(1, message.indexOf(' '));
			} else
			{
				args = new String[0];
				command = message.substring(1);
			}
			for (Command c : commands)
			{
				if (c.commandName.equalsIgnoreCase(command))
				{
					c.run(sender, args, channel);
				}
		}
	}
	
}
