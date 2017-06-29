package com.themagzuz.discord.discordmmo;

import java.util.ArrayList;
import java.util.List;

import com.themagzuz.discord.discordmmo.datatypes.Player;

import net.dv8tion.jda.core.entities.User;

public class PlayerHandler
{

	static List<Player> players = new ArrayList<Player>();
	
	/**
	 * 
	 * @param user The user to be checked
	 * @return True if the user has a registered player, otherwise false
	 */
	public static boolean HasPlayer(User user)
	{
		for (Player p : players)
		{
			if (p.user.getAsMention().equals(user.getAsMention()))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param user The mention of the user to be checked
	 * @return True if the user has a registered player, otherwise false
	 */
	public static boolean HasPlayer(String mention)
	{
		for (Player p : players)
		{
			if (p.user.getAsMention().equals(mention))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param discriminator The discriminator of the user, which is returned by the function {@link net.dv8tion.jda.core.entities.User#getDiscriminator() user.getDiscriminator()}
	 * @return True if the user has a registered player, otherwise false
	 */
	public static boolean HasPlayerDiscriminator(String discriminator)
	{
		for (Player p : players)
		{
			if (p.user.getDiscriminator().equals(discriminator))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param user The user to create the player for
	 * @param name The name of the player to create
	 * @return The player that was created, null if the process failed
	 */
	public static Player CreatePlayer(User user, String name)
	{
		if (HasPlayer(user))
			return null;
		Player p = new Player(name, user);
		players.add(p);
		return p;
	}
	
	/**
	 * @param user The user to get the player for
	 * @return The player that owns {@code user}, or null if the user has no player
	 */
	public static Player getPlayer(User user)
	{
		for (Player p : players)
		{
			if (p.user.getAsMention().equals(user.getAsMention()))
			{
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @param user The user to get the player for
	 * @return The player that owns {@code user}, or null if the user has no player
	 */
	public static Player getPlayer(String mention)
	{
		for (Player p : players)
		{
			if (p.user.getAsMention().equals(mention))
			{
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @param user The user to get the player for
	 * @return The player that owns {@code user}, or null if the user has no player
	 */
	public static Player getPlayerDiscriminator(String discriminator)
	{
		for (Player p : players)
		{
			if (p.user.getDiscriminator().equals(discriminator))
			{
				return p;
			}
		}
		return null;
	}
	
}
