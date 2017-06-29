package com.themagzuz.discord.discordmmo;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter{

	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) 
	{
		
		String message = e.getMessage().getRawContent();
		User sender = e.getAuthor();
		
		if (sender.isBot())
			return;
		
		if (message.startsWith(CommandHandler.commandPrefix))
		{
			CommandHandler.HandleCommand(message, sender, e.getChannel());
		}
	}
	
	

	
}
