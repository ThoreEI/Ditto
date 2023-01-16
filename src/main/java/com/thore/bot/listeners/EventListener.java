package com.thore.bot.listeners;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
            User user = event.getUser();
            String emoji = event.getReaction().getEmoji().getAsReactionCode();
            String channelMention = event.getChannel().getAsMention();
            String jumpURL = event.getJumpUrl();

        String message = user.getAsTag() + " reacted to a message with " + emoji + " " + channelMention;
        event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();
    }
}
