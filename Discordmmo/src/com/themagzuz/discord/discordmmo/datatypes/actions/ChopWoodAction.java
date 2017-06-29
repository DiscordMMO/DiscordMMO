package com.themagzuz.discord.discordmmo.datatypes.actions;

import java.time.Instant;
import com.themagzuz.discord.discordmmo.datatypes.Action;
import com.themagzuz.discord.discordmmo.datatypes.Player;
import com.themagzuz.discord.discordmmo.heplers.TimeHelper;

public class ChopWoodAction extends Action
{

	
	
	public ChopWoodAction(Player player)
	{
		super(player, 10);
		finishTime = Instant.now().plusSeconds(10);
	}

	int woodChopped = 0;
	
	@Override
	public void run()
	{
		super.run();
	}

	@Override
	protected void finish()
	{
		super.finish();
	}

	@Override
	public String GetActiveFormatting(boolean mention)
	{
		return (mention ? performer.user.getAsMention() : performer.name) + " is currently chopping wood, and will be done in " +  TimeHelper.TimeLeft(finishTime);
	}

	@Override
	public String GetFinishedFormatting(boolean mention)
	{
		return (mention ? performer.user.getAsMention() : performer.name) + " is done chopping wood";
	}

	@Override
	public String GetFinishedFormattingSecondPerson()
	{
		return "You are done chopping wood";
	}
	
	@Override
	public String GetActiveFormattingSecondPerson()
	{
		return  "You are currently chopping wood, and will be done in " +  TimeHelper.TimeLeft(finishTime);
	}

}
