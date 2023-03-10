package com.thore.bot.listeners;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.nio.channels.Channel;

public class MessageListener extends ListenerAdapter {
//    @Override
//    public void onMessageReceived(MessageReceivedEvent event) {
//        if (isBlackJackChannel(event.getChannel()) || isBotSpamChannel(event.getChannel())
//                || event.getAuthor().isBot()
//                || event.isWebhookMessage())
//            return;
//        String content = event.getMessage().getContentRaw();
//        BlackJackGame.BLACK_JACK_GAME_CHANNEL.sendMessage("\""+ content + "\"").queue();
//    }


    private boolean isBlackJackChannel(MessageChannel channel) {
        return channel.getId().equals(Bot.getConfig().get("BLACK_JACK_GAME_ID"));
    }

    private boolean isBotSpamChannel(MessageChannel channel) {
        return channel.getId().equals(Bot.getConfig().get("BOT_SPAM_ID"));
    }
}
