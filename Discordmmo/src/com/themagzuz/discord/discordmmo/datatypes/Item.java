package com.themagzuz.discord.discordmmo.datatypes;

public abstract class Item
{

	protected static String name;
	protected static String displayName;
	protected static int id;

	public Item(int id)
    {
        this.id = id;
    }


	public Stack CreateStack()
	{
		return new Stack(this);
	}
	
	public Stack CreateStack(int size)
	{
		return new Stack(this, size);
	}

	public static String getName()
	{
		return name;
	}

	public static String getDisplayName()
	{
		return displayName;
	}
	
	public static int GetId()
    {
        return id;
    }
	
}
