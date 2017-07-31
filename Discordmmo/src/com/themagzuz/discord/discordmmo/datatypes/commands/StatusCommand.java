package com.themagzuz.discord.discordmmo.datatypes.commands;

import com.themagzuz.discord.discordmmo.PlayerHandler;
import com.themagzuz.discord.discordmmo.Strings;
import com.themagzuz.discord.discordmmo.datatypes.Command;
import com.themagzuz.discord.discordmmo.datatypes.Player;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class StatusCommand extends Command
{

	public StatusCommand()
	{
		super();
		commandName = "status";
	}	
	
	@Override
	public void run(User sender, String[] args, MessageChannel channel)
	{
		
		//TODO: Implement viewing the status of other players
		/*
			if (args.length > 0)
			{
				System.out.println(args[0]);
				// If a standard mention was used
				if (args[0].matches("/<@[0-9]{18}>/gi"))
				{
					if (PlayerHandler.HasPlayer(args[0]))
					{
						Player target = PlayerHandler.GetPlayer(args[0]);
						channel.sendMessage(sender.getAsMention() + ": " + target.currentAction.GetActiveFormatting(false)).queue();
					}
					else
					{
						channel.sendMessage(sender.getAsMention() + ": That user is not registered").queue();
					}
					
				}
				// If a discriminator mention was used
				else if (args[0].matches("/@[A-Za-z0-9_]+#[0-9]{4}/gix"))
				{
					String discriminator = args[0].substring(args[0].indexOf('#'));
					if (!PlayerHandler.HasPlayerDiscriminator(discriminator))
					{
						channel.sendMessage(sender.getAsMention() + ": That user is not registered").queue();
					}
					else
					{
						Player target = PlayerHandler.getPlayerDiscriminator(discriminator);
						channel.sendMessage(sender.getAsMention() + ": " + target.currentAction.GetActiveFormatting(false)).queue();
					}
				}
				else
				{
					channel.sendMessage(sender.getAsMention() + ": The first argument was not recognized as a mention!").queue();
				}
			}
			else
			{
				
			}
			*/
		
		if (!PlayerHandler.RegisterOrLogin(sender))
		{
			channel.sendMessage(sender.getAsMention() + Strings.NOT_REGISTERED).queue();
			return;
		}
		Player player = PlayerHandler.GetPlayer(sender);
		player.Tick();
		channel.sendMessage(player.getCurrentAction().GetActiveFormattingSecondPerson()).queue();
		
	}

}
