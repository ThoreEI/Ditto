package com.thore.bot.listeners;

import com.thore.bot.io.loader.TxtFileLoader;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EventListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getMessage().getAuthor().isBot())
            Objects.requireNonNull(event.getGuild().getDefaultChannel()).asTextChannel().sendMessage(TxtFileLoader.loadFile("src/main/java/com/thore/bot/io/files/jokes.txt")).queue();
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        String message = Objects.requireNonNull(event.getUser()).getAsTag() + "/n Reaction: " + event.getReaction().getEmoji().getAsReactionCode() + " " + event.getChannel().getAsMention();
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).asTextChannel().sendMessage(message).queue();
    }
}
