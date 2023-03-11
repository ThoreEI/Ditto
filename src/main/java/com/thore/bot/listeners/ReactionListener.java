package com.thore.bot.listeners;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import static com.thore.bot.listeners.EventManager.isBlackJackChannel;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getUser().isBot() || !isBlackJackChannel(event.getChannel()))
            return;
        String username = event.getUser().getName();
        String amountChips = event.getButton().getLabel();
        event.getChannel().sendMessage(username + " setzt " + amountChips).queue();
    }
}
