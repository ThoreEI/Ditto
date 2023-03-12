package com.thore.bot.listeners;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

import static com.thore.bot.listeners.EventManager.isBlackJackChannel;

public class ButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getUser().isBot() || !isBlackJackChannel(event.getChannel()))
            return;
        String amountChips = event.getButton().getLabel();
        if (Objects.equals(event.getButton().getId(), "btnChip1") /* TODO  player chips */)
            event.getChannel().sendMessage(event.getUser().getName() + " setzt " + amountChips).queue();
    }
}
