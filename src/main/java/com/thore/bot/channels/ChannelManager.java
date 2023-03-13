package com.thore.bot.channels;

import com.thore.bot.Bot;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ChannelManager {
    public final static TextChannel BLACK_JACK_GAME_CHANNEL = Bot.getJda().getTextChannelById(Bot.getConfig().get("BLACK_JACK_GAME_ID"));
    public final static TextChannel BOT_SPAM_CHANNEL = Bot.getJda().getTextChannelById(Bot.getConfig().get("BOT_SPAM_ID"));
    public final static TextChannel JOKES_CHANNEL = Bot.getJda().getTextChannelById(Bot.getConfig().get("JOKES_ID"));
}
