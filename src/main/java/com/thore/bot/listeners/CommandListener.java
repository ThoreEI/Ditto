package com.thore.bot.listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("welcome"))
            event.reply("Welcome to the server" + event.getUser().getAsTag()).queue();
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List <CommandData> commandDataList = new ArrayList<>();
        commandDataList.add(Commands.slash("welcome", "greetings"));
        event.getGuild().updateCommands().addCommands(commandDataList).queue();
    }
}
