package com.themagzuz.discord.discordmmo.datatypes;

import java.time.Instant;

import com.themagzuz.discord.discordmmo.datatypes.Player;

public abstract class Action implements Runnable
{
	
	protected String name;
	protected Instant finishTime;
	
	protected int actionTime;
	
	/**
	 * The player who is performing the action
	 */
	protected Player performer;

	public Action(Player player, int length)
	{
		actionTime = length;
		performer = player;
	}
	
	@Override
	public void run()
	{
		if (Instant.now().isAfter(finishTime))
		{
			finish();
		}
	}
	
	protected void finish()
	{
		if (performer.messageOnFinish)
		{
			performer.privateChannel.sendMessage(GetFinishedFormattingSecondPerson()).queue();
		}
		performer.Idle();
	}
	
	/**
	 * @param mention Should the user be mentioned?
	 * @return What will be displayed while the player is doing the action  
	 */
	public abstract String GetActiveFormatting(boolean mention);
	
	/**
	 * @param mention Should the user be mentioned?
	 * @return What will be displayed when the player has finished the action
	 */
	public abstract String GetFinishedFormatting(boolean mention);
	
	/**
	 * @return What will be pm'ed to the player when they have finished the action
	 */
	public abstract String GetFinishedFormattingSecondPerson();
	
	public abstract String GetActiveFormattingSecondPerson();
	
	public String GetName()
	{
		return name;
	}
	
	public Instant GetFinishTime()
	{
		return finishTime;
	}
	
}
