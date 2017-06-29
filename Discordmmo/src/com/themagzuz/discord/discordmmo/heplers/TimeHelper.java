package com.themagzuz.discord.discordmmo.heplers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class TimeHelper
{


	public static String TimeLeft(Instant until)
	{
		if (!until.isAfter(Instant.now()))
		{
			return "00:00:00";
		}
		long millis = Instant.now().until(until, ChronoUnit.MILLIS);
		return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis),
				TimeUnit.MILLISECONDS.toSeconds(millis));
	}
	
}
