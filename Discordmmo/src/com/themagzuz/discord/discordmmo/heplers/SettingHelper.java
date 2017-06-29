package com.themagzuz.discord.discordmmo.heplers;

import com.themagzuz.discord.discordmmo.exceptions.InvalidParseException;

public class SettingHelper
{

	public static boolean ToBool(String parse) throws InvalidParseException
	{
		switch (parse.toLowerCase())
		{
			case "yes":
			case "true":
			case "1":
				return true;
			case "no":
			case "false":
			case "0":
				return false;
			default:
				throw new InvalidParseException("Invalid input \"" + parse + "\" was given");
		}
	}
	
}
