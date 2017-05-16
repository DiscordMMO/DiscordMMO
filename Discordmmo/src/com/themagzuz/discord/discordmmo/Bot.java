package com.themagzuz.discord.discordmmo;

import javax.security.auth.login.LoginException;

import com.themagzuz.util.configload.Config;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Bot {

	public static JDA jda;
	
	/**
	 * DO NOT CHANGE THIS VARIABLE. THE ONLY REASON THIS IS NOT FINAL IS SO THAT
	 * IT CAN BE SET TO A VALUE FROM CONFIG
	 */
	private static String TOKEN;
	
	public static void main(String[] args) {
		try {
			jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(TOKEN)/*.setGame(Game.of("with ma dick ( \u0361\u00B0\u035C\u0296 \u0361\u00B0 )"))*/.buildBlocking();
		} catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException e) {
			e.printStackTrace();
		}
	}

	static void LoadVars()
	{
		Config.SetConfigFile(System.getProperty("user.dir") + "\\config.cfg");
		TOKEN = Config.GetString("token");
	}
	
	public static String getToken()
	{
		return TOKEN;
	}
	
}
