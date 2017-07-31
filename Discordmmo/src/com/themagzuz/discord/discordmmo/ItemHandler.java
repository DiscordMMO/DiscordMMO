package com.themagzuz.discord.discordmmo;

import java.util.HashMap;
import java.util.Map;

import com.themagzuz.discord.discordmmo.datatypes.Item;
import com.themagzuz.discord.discordmmo.datatypes.items.ItemWood;

public final class ItemHandler
{

	private ItemHandler() {}
	
	public static Map<String, Item> items = new HashMap<String, Item>();

	public static Map<Integer, Item> idToItem = new HashMap<Integer, Item>();

	static
    {
        SetupItems();
    }


	public static void SetupItems()
	{
	    int itemCount = 0;
		items.put(ItemWood.getName(), new ItemWood(itemCount++));
		idToItem.put(ItemWood.GetId(), getItem(ItemWood.getName()));
	}
	
	public static Item getItem(String name)
	{
		return items.get(name);
	}
    public static Item getItem(int id)
    {
	    return idToItem.get(id);
    }


}
