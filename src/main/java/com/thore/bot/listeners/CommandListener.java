package com.thore.bot.listeners;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import com.thore.bot.io.reader.FileReader;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommandListener extends ListenerAdapter {
    public final static  String BOT_CHANNEL_ID = Bot.getConfig().get("BOT_CHANNEL");

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        ArrayList <CommandData> commandDataList = new ArrayList<>();
        commandDataList.add(Commands.slash("joke", "Witz gefällig?"));
        commandDataList.add(Commands.slash("blackjack", "BlackJack-Spiel."));

        event.getGuild().updateCommands().addCommands(commandDataList).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
      if (BOT_CHANNEL_ID.equals(event.getChannel().getId()))
            return;
        if (event.getName().equals("joke")) {
            event.reply(FileReader.loadJoke()).queue();
        } else if (event.getName().equals("blackjack")) {
            event.reply("Blackjack is starting...").queue();
            // TODO Spieleranzahl, Einsatz
            new BlackJackGame();
        }
    }
}
