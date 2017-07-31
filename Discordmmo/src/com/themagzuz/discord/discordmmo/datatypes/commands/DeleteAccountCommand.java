package com.themagzuz.discord.discordmmo.datatypes.commands;

import com.themagzuz.discord.discordmmo.CommandHandler;
import com.themagzuz.discord.discordmmo.PlayerHandler;
import com.themagzuz.discord.discordmmo.Strings;
import com.themagzuz.discord.discordmmo.datatypes.Command;
import com.themagzuz.discord.discordmmo.datatypes.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by TheMagzuz on 31-07-2017.
 *
 * @author TheMagzuz
 */
public class DeleteAccountCommand extends Command
{

    public DeleteAccountCommand()
    {
        super();
        commandName = "delacc";
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
        if (args.length <= 0 || args[0] == null || (!args[0].equalsIgnoreCase(player.name) && !args[0].equalsIgnoreCase(sender.getName())))
        {
            channel.sendMessage(sender.getAsMention() + ": Usage: " + CommandHandler.commandPrefix + "delacc <username>").queue();
            return;
        }

    }
}
