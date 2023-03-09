package com.thore.bot.listeners;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StateListener extends ListenerAdapter {
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        MessageChannel channel = Objects.requireNonNull(
                event.getGuild().getDefaultChannel()).asTextChannel();
        String newMember = Objects.requireNonNull(event.getUser()).getAsTag();
        channel.sendMessage("Welcome to the server " + newMember).queue();
    }
}
