package com.thore.bot.listeners;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import com.thore.bot.io.reader.FileReader;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandListener extends ListenerAdapter {
    public final static String BOT_SPAM_CHANNEL = Bot.getConfig().get("BOT_SPAM_ID");

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (BOT_SPAM_CHANNEL.equals(event.getChannel().getId()))
            return;
        else if (event.getName().equals("joke"))
            event.reply(FileReader.loadJoke()).queue();
        else if (event.getName().equals("blackjack")) {
            Category blackjack = Objects.requireNonNull(
                            event.getGuild())
                    .getCategoriesByName("blackjack", true)
                    .get(0);
            TextChannel temporaryChannel = blackjack.createTextChannel("Black Jack Game " + blackjack.getChannels().size())
                    .setTopic("This is a temporary Black Jack channel.")
                    .complete();
            event.reply("Blackjack is starting...").queue();
            new BlackJackGame();
        }

        // Erinnerung --> bump --> 100 Coins --> BlackJack --> Bestenliste
    }
}
