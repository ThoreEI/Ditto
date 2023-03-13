package com.thore.bot;
import com.thore.bot.Bot;
import net.dv8tion.jda.api.entities.channel.Channel;

public class IDVerifier {

    public static boolean isBlackJackChannel(Channel channel) {
        return channel.getId().equals(Bot.getConfig().get("BLACK_JACK_GAME_ID"));
    }
    public static boolean isBotSpamChannel(Channel channel) {
        return channel.getId().equals(Bot.getConfig().get("BOT_SPAM_ID"));
    }
    public static boolean isJokesChannel(Channel channel) {
        return channel.getId().equals(Bot.getConfig().get("JOKES_ID"));
    }
}