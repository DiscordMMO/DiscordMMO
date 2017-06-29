package com.themagzuz.discord.discordmmo.datatypes.commands;

import java.util.Arrays;

import com.themagzuz.discord.discordmmo.datatypes.Command;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class PingCommand extends Command
{

	public PingCommand()
	{
		super();
		commandName = "ping";
	}
			
	
	@Override
	public void run(User sender, String[] args, MessageChannel channel)
	{
		channel.sendMessage(sender.getAsMention() + " sent with following arguments: " + Arrays.toString(args)).queue();
		System.out.println(sender.getAsMention());
		System.out.println(Arrays.toString(args));
	}

}
