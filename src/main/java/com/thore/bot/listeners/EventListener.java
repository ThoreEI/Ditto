package com.thore.bot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            String joke;
        try {
             joke = new BufferedReader(new FileReader("C:/Users/UnknownUser/IdeaProjects/Bot/src/media/jokes.txt")).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!event.getMessage().getAuthor().isBot())
            Objects.requireNonNull(event.getGuild().getDefaultChannel()).asTextChannel().sendMessage(joke).queue();
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        String message = Objects.requireNonNull(event.getUser()).getAsTag() + "/n Reaction: " + event.getReaction().getEmoji().getAsReactionCode() + " " + event.getChannel().getAsMention();
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).asTextChannel().sendMessage(message).queue();
    }
}
