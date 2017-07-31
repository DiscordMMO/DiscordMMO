package com.themagzuz.discord.discordmmo.datatypes.commands;

import com.themagzuz.discord.discordmmo.PlayerHandler;
import com.themagzuz.discord.discordmmo.Strings;
import com.themagzuz.discord.discordmmo.datatypes.Command;
import com.themagzuz.discord.discordmmo.datatypes.Player;
import com.themagzuz.discord.discordmmo.datatypes.Stack;

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
		if (!PlayerHandler.RegisterOrLogin(sender))
		{
			channel.sendMessage(sender.getAsMention() + Strings.NOT_REGISTERED).queue();
			return;
		}
		Player player = PlayerHandler.GetPlayer(sender);
		String msg = sender.getAsMention() + ": Displaying inventory\n";
		int freeSlots = 0;
		for (int i = 0; i < player.inventory.stacks.length; i++)
		{
			Stack s = player.inventory.stacks[i];
			if (i % 5 == 0)
			{
				msg += "\n";
			}
			if (s == null || s.item == null)
			{
				msg += "[Empty]";
			}
			else
			{
			    String app = s.item.getDisplayName() + String.format("(%s)", s.size);
			    while (app.length() < "[Empty]".length())
                {
                    app += " ";
                }
				msg += app + " ";
			}
			if (s == null || s.item == null)
			{
				freeSlots++;
			}
		}
		msg += "\nYou have " + freeSlots + "/27 slots free";
		
		channel.sendMessage(msg).queue();
		
	}

}
