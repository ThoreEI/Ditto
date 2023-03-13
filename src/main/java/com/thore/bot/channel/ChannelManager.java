package com.thore.bot.channel;
import com.thore.bot.Bot;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ChannelManager {
    private final static TextChannel BLACK_JACK_GAME_CHANNEL = Bot.getJda().getTextChannelById(Bot.getConfig().get("BLACK_JACK_GAME_ID"));
    private final static TextChannel BOT_SPAM_CHANNEL = Bot.getJda().getTextChannelById(Bot.getConfig().get("BOT_SPAM_ID"));
    private final static TextChannel JOKES_CHANNEL = Bot.getJda().getTextChannelById(Bot.getConfig().get("JOKES_ID"));
    public static TextChannel getJokesChannel() {
        return JOKES_CHANNEL;
    }
    public static TextChannel getBotSpamChannel() {
        return BOT_SPAM_CHANNEL;
    }
    public static TextChannel getBlackJackGameChannel() {
        return BLACK_JACK_GAME_CHANNEL;
    }
}
