package com.themagzuz.discord.discordmmo.datatypes.actions;

import com.themagzuz.discord.discordmmo.datatypes.Action;
import com.themagzuz.discord.discordmmo.datatypes.Player;

public class IdleAction extends Action
{

	public IdleAction(Player player)
	{
		super(player, Integer.MAX_VALUE);
	}
	
	@Override
	public void run()
	{
		System.out.println("Idle");
	}

	@Override
	public String GetActiveFormatting(boolean mention)
	{
		return (mention ? performer.user.getAsMention() : performer.name) + " is currently idle";
	}

	/**
	 * If everything works correctly, this should never be called
	 */
	@Override
	public String GetFinishedFormatting(boolean mention)
	{
		return null;
	}

	/**
	 * If everything works correctly, this should never be called
	 */
	@Override
	public String GetFinishedFormattingSecondPerson()
	{
		return null;
	}
	
	@Override
	public String GetActiveFormattingSecondPerson()
	{
		return "You are currently idle";
	}

}
