package com.themagzuz.discord.discordmmo.datatypes;

public abstract class Item
{

	public String name;
	
	public Stack CreateStack()
	{
		return new Stack(this);
	}
	
	public Stack CreateStack(int size)
	{
		return new Stack(this, size);
	}
	
}
