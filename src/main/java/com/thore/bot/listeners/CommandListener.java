package com.thore.bot.listeners;

import com.thore.bot.games.BlackJackGame;
import com.thore.bot.io.loader.TxtFileLoader;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("hello"))
            event.reply("Welcome to the server " + event.getUser().getAsTag()).queue();
        if (event.getName().equals("joke"))
            event.reply(TxtFileLoader.loadFile("src/main/java/com/thore/bot/io/files/jokes.txt")).queue();

        if (event.getName().equals("blackjack")) {
            event.reply("Blackjack is starting...").queue();
            new BlackJackGame();
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        ArrayList <CommandData> commandDataList = new ArrayList<>();
        commandDataList.add(Commands.slash("hello", "greetings"));
        commandDataList.add(Commands.slash("joke", "something funny"));
        event.getGuild().updateCommands().addCommands(commandDataList).queue();
    }
}
