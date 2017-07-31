package com.themagzuz.discord.discordmmo.datatypes.commands;

import com.themagzuz.discord.discordmmo.DatabaseHandler;
import com.themagzuz.discord.discordmmo.Strings;
import com.themagzuz.discord.discordmmo.datatypes.Command;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by TheMagzuz on 31-07-2017.
 *
 * @author TheMagzuz
 */
public class SaveCommand extends Command
{

    public SaveCommand()
    {
        commandName = "save";
    }

    @Override
    public void run(User sender, String[] args, MessageChannel channel)
    {
        if (Strings.adminMentions.contains(sender.getAsMention()))
        {
            channel.sendMessage("Starting save").queue();
            DatabaseHandler.SaveAll();
            channel.sendMessage("Save done").queue();
        }
        else
        {
            channel.sendMessage(sender.getAsMention() + ": You do not have permission to do that!").queue();
            return;
        }
    }
}
