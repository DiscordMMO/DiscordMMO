package com.themagzuz.discord.discordmmo;

import java.util.HashMap;
import java.util.Map;

import com.themagzuz.discord.discordmmo.datatypes.Item;
import com.themagzuz.discord.discordmmo.datatypes.items.ItemWood;

public final class ItemHandler
{

	private ItemHandler() {}
	
	static Map<String, Item> items = new HashMap<String, Item>();
	
	public static void SetupItems()
	{
		items.put("wood", new ItemWood());
	}
	
	public static Item getItem(String name)
	{
		return items.get(name);
	}
	
}
