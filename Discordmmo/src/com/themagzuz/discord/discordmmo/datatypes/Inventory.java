package com.themagzuz.discord.discordmmo.datatypes;

public class Inventory
{

	public Stack[] stacks = new Stack[27]; 
	
	/**
	 * Adds a stack to the inventory if there is space
	 * @param toAdd The item to add to the inventory
	 * @return True if the item was successfully added to the inventory
	 */
	public boolean AddStack(Stack toAdd)
	{
		for (int i = 0; i < stacks.length; i++)
		{
			if (stacks[i].item.equals(toAdd.item))
			{
				stacks[i].size += toAdd.size;
				return true;
			}
		}
		for (int i = 0; i < stacks.length; i++)
		{
			if (stacks[i] == null)
			{
				stacks[i] = toAdd;
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Removes the stack from the {@code index}th slot of the inventory
	 * @param index The index of the stack that should be removed
	 * @return True if the item was successfully removed
	 */
	public boolean RemoveStack(int index)
	{
		if (stacks[index] == null)
			return false;
		stacks[index] = null;
		return true;
	}
	
	/**
	 * Removes the stack from the inventory of type {@code item}
	 * @param item The item type to be removed
	 * @return True if the item was successfully removed
	 */
	public boolean RemoveItem(Item item)
	{
		for (int i = 0; i < stacks.length; i++)
		{
			if (stacks[i].item.equals(item))
			{
				stacks[i] = null;
				return true;
			}
		}
		return false;
	}
	
}
