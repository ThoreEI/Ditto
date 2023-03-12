package com.thore.bot.listeners;
import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import com.thore.bot.io.reader.FileReader;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.thore.bot.listeners.EventManager.*;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (isBotSpamChannel(event.getChannel())) {
            event.reply("Hey" + event.getUser().getName() + "! Raus aus meinem Channel!").queue();
            return;
        }
        else if (event.getName().equals("joke") && isJokesChannel(event.getChannel()))
            event.reply(FileReader.loadJoke()).queue();
        else if (event.getName().equals("blackjack") && isBlackJackChannel(event.getChannel())) {
            Category blackjack = Objects.requireNonNull(
                            event.getGuild())
                    .getCategoriesByName("blackjack", true)
                    .get(0);
            TextChannel temporaryChannel = blackjack.createTextChannel("Black Jack Game " + blackjack.getChannels().size())
                    .setTopic("This is a temporary Black Jack channel.")
                    .complete();
            event.reply("Blackjack is starting...").queue();
            new BlackJackGame(temporaryChannel);
        }
        System.out.println();

        // Erinnerung --> bump --> 100 Coins --> BlackJack --> Bestenliste
    }
}
