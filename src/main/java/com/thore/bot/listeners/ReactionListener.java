package com.thore.bot.listeners;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        String user = Objects.requireNonNull(event.getUser()).getName();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String message = "Reaction from " + user + ": " + emoji;
        event.getChannel().sendMessage(message).queue();
    }

}
