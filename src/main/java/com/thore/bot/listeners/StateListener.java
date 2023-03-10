package com.thore.bot.listeners;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class StateListener extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        ArrayList<CommandData> commandDataList = new ArrayList<>();
        commandDataList.add(Commands.slash("joke", "Witz gefällig?"));
        commandDataList.add(Commands.slash("blackjack", "BlackJack-Spiel."));
        event.getGuild().updateCommands().addCommands(commandDataList).queue();
    }

    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        MessageChannel channel = Objects.requireNonNull(
                event.getGuild().getDefaultChannel()).asTextChannel();
        String newMember = Objects.requireNonNull(event.getUser()).getAsTag();
        channel.sendMessage("Welcome to the server " + newMember).queue();
    }


}
