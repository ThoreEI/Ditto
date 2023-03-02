package com.thore.bot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class VoiceListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (content.equals("!ping")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("PONG!").queue();
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        String message = Objects.requireNonNull(event.getUser()).getAsTag() + "/n Reaction: " + event.getReaction().getEmoji().getAsReactionCode() + " " + event.getChannel().getAsMention();
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).asTextChannel().sendMessage(message).queue();
    }
}