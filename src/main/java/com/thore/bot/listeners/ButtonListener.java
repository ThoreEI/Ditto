package com.thore.bot.listeners;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Objects;

import static com.thore.bot.listeners.EventManager.isBlackJackChannel;

public class ButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getUser().isBot()||!isBlackJackChannel(event.getChannel()))
            return;
        int amountChips = Integer.parseInt(event.getButton().getLabel());
       // if (amountChips==1||amountChips==5||amountChips==10||amountChips==25||amountChips==50 ||amountChips==100||amountChips==200||amountChips==500||amountChips==1000||amountChips==5000)
            if (Objects.equals(event.getButton().getId(), "btnChip" + amountChips))
                event.getUser().getName();
        event.getChannel().sendMessage(event.getUser().getName() + " setzt " + amountChips).queue();
    }
}
