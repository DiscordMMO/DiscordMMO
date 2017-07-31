package com.themagzuz.discord.discordmmo.datatypes.commands;

import com.themagzuz.discord.discordmmo.PlayerHandler;
import com.themagzuz.discord.discordmmo.datatypes.Command;
import com.themagzuz.discord.discordmmo.datatypes.Player;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class RegisterCommand extends Command
{

	public RegisterCommand()
	{
		super();
		commandName = "register";
	}
	
	@Override
	public void run(User sender, String[] args, MessageChannel channel)
	{
		if (PlayerHandler.RegisterOrLogin(sender))
		{
			channel.sendMessage(sender.getAsMention() + ": Failed to register. You already have a player!").queue();
			return;
		}
		Player created;

		if (args.length > 0)
		{
			created = PlayerHandler.CreatePlayer(sender, args[0]);
		}
		else
		{
			created = PlayerHandler.CreatePlayer(sender, sender.getName());
		}
		if (created != null && created.privateChannel != null)
		{
			created.privateChannel.sendMessage("You have been registered under the name " + created.name).queue();
			created.privateChannel.sendMessage("You can change your preferences with the command $pref").queue();
		}
	}

}
