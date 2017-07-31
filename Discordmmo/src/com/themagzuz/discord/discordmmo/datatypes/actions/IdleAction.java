package com.themagzuz.discord.discordmmo.datatypes.actions;

import com.themagzuz.discord.discordmmo.datatypes.Action;
import com.themagzuz.discord.discordmmo.datatypes.Player;

public class IdleAction extends Action
{

	public IdleAction(Player player, boolean announceToPlayer)
	{
		super(player, Integer.MAX_VALUE, announceToPlayer);
		name = "idle";
	}
	
	@Override
	public void run()
	{
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

    @Override
    public String GetStartedFormattingSecondPerson()
    {
        return "You have started idling";
    }

    @Override
    public String GetStartedFormattingThirdPerson(boolean mention)
    {
        return (mention ? performer.user.getAsMention() : performer.name) + " has started idling";
    }
}
