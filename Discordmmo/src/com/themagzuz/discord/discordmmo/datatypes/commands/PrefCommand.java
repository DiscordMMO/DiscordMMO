package com.themagzuz.discord.discordmmo.datatypes.commands;

import com.themagzuz.discord.discordmmo.PlayerHandler;
import com.themagzuz.discord.discordmmo.datatypes.Command;
import com.themagzuz.discord.discordmmo.datatypes.Player;
import com.themagzuz.discord.discordmmo.exceptions.InvalidParseException;
import com.themagzuz.discord.discordmmo.heplers.SettingHelper;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class PrefCommand extends Command
{

	public PrefCommand()
	{
		super();
		commandName = "pref";
	}
	
	@Override
	public void run(User sender, String[] args, MessageChannel channel)
	{
		if (!PlayerHandler.HasPlayer(sender))
		{
			channel.sendMessage(sender.getAsMention() + ": You are not registered! Register with the command $register [name]").queue();
			return;
		}
		Player player = PlayerHandler.getPlayer(sender);
		if (args.length <= 0)
		{
			String msg = sender.getAsMention() + ": Usage: $pref <setting> <value>\n"
					+ "Preferences:\n"
					+ "Name (setting name): value\n"
					+ "Mention in messages (mention): " + player.mention +
					"\nPrivate messages when finished (pmOnFinish): " + player.messageOnFinish;
			channel.sendMessage(msg).queue();
		}
		else if (args.length == 1)
		{
			switch (args[0].toLowerCase())
			{
				case "mention":
					channel.sendMessage("You are " + (player.mention ? "" : "not ") + "being mentioned in messages").queue();
					break;
				case "pmonfinish":
					channel.sendMessage("You are " + (player.messageOnFinish ? "" : "not ") + "receiving private messages when a task is finished").queue();
					break;
			}
		}
		else if (args.length >= 2)
		{
			switch (args[0].toLowerCase())
			{
				case "mention":
					try
					{
						boolean set = SettingHelper.ToBool(args[1]);
						player.mention = set;
						channel.sendMessage(sender.getAsMention() + ": Set setting \"" + args[0] + "\" to " + set).queue();
					}
					catch (InvalidParseException e)
					{
						channel.sendMessage(sender.getAsMention() + ": \"" + args[1] + "\" could not be recognized as a valid value").queue();
					}
					break;
				case "pmonfinish":
					try
					{
						boolean set = SettingHelper.ToBool(args[1]);
						player.messageOnFinish = set;
						channel.sendMessage(sender.getAsMention() + ": Set setting \"" + args[0] + "\" to " + set).queue();
					}
					catch (InvalidParseException e)
					{
						channel.sendMessage(sender.getAsMention() + ": \"" + args[1] + "\" could not be recognized as a valid value").queue();
					}
					break;
				default:
					channel.sendMessage(sender.getAsMention() + ": Setting \"" + args[0] + "\" could not be recognized").queue();
			}
		}
	}

}
