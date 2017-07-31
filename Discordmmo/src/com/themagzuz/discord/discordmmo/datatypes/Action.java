package com.themagzuz.discord.discordmmo.datatypes;

import com.themagzuz.discord.discordmmo.datatypes.actions.ChopWoodAction;
import com.themagzuz.discord.discordmmo.datatypes.actions.IdleAction;
import org.apache.http.impl.io.ContentLengthInputStream;

import java.lang.reflect.Constructor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Action
{
	
	protected static String name;
	public Instant finishTime;
	
	protected int actionTime;
	
	/**
	 * The player who is performing the action
	 */
	protected Player performer;

	public Action(Player player, int length, boolean announceToPlayer)
	{
		actionTime = length;
		performer = player;
		finishTime = Instant.now().plus(actionTime, ChronoUnit.SECONDS);
		if (player.sendPrivateMessages && announceToPlayer)
		{
			performer.privateChannel.sendMessage(GetStartedFormattingSecondPerson()).queue();
		}
	}

	public static Action GetActionFromName(String name, Player player)
	{
		return GetActionFromName(name, player, player.sendPrivateMessages);
	}

	public static Action GetActionFromName(String name, Player player, boolean announce)
	{
		switch(name.toLowerCase())
		{
			case "idle":
				return new IdleAction(player, announce);
			case "chopwood":
				return new ChopWoodAction(player, announce);
			default:
				return null;
		}
	}

	public void run()
	{
		if (Instant.now().isAfter(finishTime))
		{
			finish();
		}
	}
	
	protected void finish()
	{
		if (performer.sendPrivateMessages)
		{
			performer.privateChannel.sendMessage(GetFinishedFormattingSecondPerson()).queue();
		}
		performer.Idle(false);
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

	public abstract String GetStartedFormattingSecondPerson();

	public abstract String GetStartedFormattingThirdPerson(boolean mention);

	public static String GetName()
	{
		return name;
	}
	
	public Instant GetFinishTime()
	{
		return finishTime;
	}
	
}
