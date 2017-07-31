package com.themagzuz.discord.discordmmo.datatypes.actions;

import java.time.Instant;

import com.themagzuz.discord.discordmmo.ItemHandler;
import com.themagzuz.discord.discordmmo.datatypes.Action;
import com.themagzuz.discord.discordmmo.datatypes.Item;
import com.themagzuz.discord.discordmmo.datatypes.Player;
import com.themagzuz.discord.discordmmo.datatypes.items.ItemWood;
import com.themagzuz.discord.discordmmo.heplers.TimeHelper;

public class ChopWoodAction extends Action
{

	int woodChopped = 1;
	
	public ChopWoodAction(Player player, boolean announceToPlayer)
	{
		super(player, 10, announceToPlayer);
		name = "chopWood";
		finishTime = Instant.now().plusSeconds(10);
	}

	
	@Override
	public void run()
	{
		super.run();
	}

	@Override
	protected void finish()
	{

        Item item = ItemHandler.getItem(ItemWood.getName());
        if (item == null)
            System.out.println("Item");
        performer.inventory.AddStack(item.CreateStack(woodChopped));
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

    @Override
    public String GetStartedFormattingSecondPerson()
    {
        return "You have started chopping wood. You will be done in " + TimeHelper.TimeLeft(finishTime);
    }

    @Override
    public String GetStartedFormattingThirdPerson(boolean mention)
    {
        return (mention ? performer.user.getAsMention() : performer.name) + " has started chopping wood";
    }
}
