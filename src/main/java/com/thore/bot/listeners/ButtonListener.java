package com.thore.bot.listeners;

import net.dv8tion.jda.api.entities.Message;
import com.thore.bot.games.blackJack.domain.Player;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.beans.EventHandler;
import java.util.Objects;

import static com.thore.bot.IDVerifier.isBlackJackChannel;

public class ButtonListener implements EventListener {

    private Player player;
    public ButtonListener(Player player) {
        this.player = player;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ButtonInteractionEvent buttonEvent) {
            if (buttonEvent.getUser().isBot()||!isBlackJackChannel(buttonEvent.getChannel()))
                return;
            if (buttonEvent.getButton().getId() != null && buttonEvent.getButton().getId().startsWith("btnChip")) {
                int amountChips = Integer.parseInt(buttonEvent.getButton().getLabel());
                // if (amountChips==1||amountChips==5||amountChips==10||amountChips==25||amountChips==50 ||amountChips==100||amountChips==200||amountChips==500||amountChips==1000||amountChips==5000)
                if (Objects.equals(buttonEvent.getButton().getId(), "btnChip" + amountChips))
                    if (player.chips >= amountChips){
                        buttonEvent.getChannel().sendMessage(buttonEvent.getUser().getName() + " setzt " + amountChips).queue();
//                        player.

                    }
                    else{
                        // böse
                    }
            }
        }
    }

}
