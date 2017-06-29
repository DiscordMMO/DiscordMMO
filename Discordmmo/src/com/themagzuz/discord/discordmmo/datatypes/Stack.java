package com.themagzuz.discord.discordmmo.datatypes;

public class Stack
{

	public Item item;
	public int size;
	
	public Stack(Item _item)
	{
		this(_item, 1);
	}
	
	public Stack(Item _item, int _size)
	{
		item = _item;
		size = _size;
	}
	
}
