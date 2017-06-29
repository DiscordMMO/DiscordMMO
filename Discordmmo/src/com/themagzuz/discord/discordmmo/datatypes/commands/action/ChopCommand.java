package com.themagzuz.discord.discordmmo.datatypes.commands.action;

import com.themagzuz.discord.discordmmo.datatypes.ActionCommand;
import com.themagzuz.discord.discordmmo.datatypes.actions.ChopWoodAction;

public class ChopCommand extends ActionCommand<ChopWoodAction>
{

	public ChopCommand()
	{
		super("chop", ChopWoodAction.class);
	}

}
