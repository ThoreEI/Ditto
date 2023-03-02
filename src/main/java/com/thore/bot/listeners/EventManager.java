package com.thore.bot.listeners;

import com.thore.bot.io.output.Messenger;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class EventManager implements EventListener {
    // private final static String BOT_SPAM_ID = "1065096620636643410";

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof GuildMemberJoinEvent)
            onGuildMemberJoin((GuildMemberJoinEvent) event);
        if (event instanceof MessageReceivedEvent)
            onMessageReceived((MessageReceivedEvent) event);
        if (event instanceof MessageReactionAddEvent)
            onMessageReactionAdd((MessageReactionAddEvent) event);
    }

    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        String user = Objects.requireNonNull(event.getUser()).getName();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        MessageChannel channel = event.getChannel();
        String message = "Reaction from " + user + ": " + emoji;
        Messenger.sendMessage(channel, message);
    }

    private void onMessageReceived(MessageReceivedEvent event) {
//        if (event.getAuthor().isBot() || !event.getChannel().getId().equals(BOT_SPAM_ID))
//            return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel(); // TODO: in bot spam channel
        if (content.equals("!card"))
            Messenger.sendMessage(channel, content);
    }

    private static void onGuildMemberJoin(GuildMemberJoinEvent event) {
       System.out.println("Welcome to the server " + event.getUser().getAsTag());
    }
}
