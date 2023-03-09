package com.thore.bot.listeners;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getId().equals(Bot.getConfig().get("BLACK_JACK_GAME_ID"))
                || event.getChannel().getId().equals(Bot.getConfig().get("BOT_SPAM_ID"))
                || event.getAuthor().isBot()
                || event.isWebhookMessage())
            return;
        String content = event.getMessage().getContentRaw();
       BlackJackGame.BLACK_JACK_GAME_CHANNEL.sendMessage("\""+ content + "\"").queue();
    }
}