package com.themagzuz.discord.discordmmo.datatypes.commands;

import com.themagzuz.discord.discordmmo.PlayerHandler;
import com.themagzuz.discord.discordmmo.Strings;
import com.themagzuz.discord.discordmmo.datatypes.Command;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class InventoryCommand extends Command
{

	public InventoryCommand()
	{
		super();
		commandName = "inventory";
	}
	
	@Override
	public void run(User sender, String[] args, MessageChannel channel)
	{
		if (!PlayerHandler.HasPlayer(sender))
		{
			channel.sendMessage(sender.getAsMention() + Strings.NOT_REGISTERED);
			return;
		}
	}

}
