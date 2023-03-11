package com.thore.bot.listeners;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static com.thore.bot.listeners.EventManager.isBlackJackChannel;
import static com.thore.bot.listeners.EventManager.isBotSpamChannel;

public class ReactionListener extends ListenerAdapter {

//    @Override
//    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
//        super.onMessageReactionAdd(event);
//    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getUser().isBot() || !isBlackJackChannel(event.getChannel()))
            return;
        String username = event.getUser().getName();
        String amountChips = event.getButton().getLabel();
        event.getChannel().sendMessage(username + " setzt " + amountChips).queue();
    }
}
