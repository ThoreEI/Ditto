package com.thore.bot.listeners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class EventManager implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof GuildMemberJoinEvent)
            onGuildMemberJoin((GuildMemberJoinEvent) event);
    }

    private static void onGuildMemberJoin(GuildMemberJoinEvent event) {
        System.out.println("Welcome to the server " + event.getUser().getAsTag());
    }
}
