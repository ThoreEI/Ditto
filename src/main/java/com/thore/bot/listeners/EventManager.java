package com.thore.bot.listeners;
import com.thore.bot.Bot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class EventManager implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof GuildMemberJoinEvent)
            new StateListener().onGuildMemberJoin((GuildMemberJoinEvent) event);
        if (event instanceof MessageReceivedEvent)
            new MessageListener().onMessageReceived((MessageReceivedEvent) event);
        if (event instanceof MessageReactionAddEvent)
            new ReactionListener().onMessageReactionAdd((MessageReactionAddEvent) event);
        if (event instanceof ButtonInteractionEvent)
            new ReactionListener().onButtonInteraction((ButtonInteractionEvent) event);
    }

    public static boolean isBlackJackChannel(MessageChannel channel) {
        return channel.getId().equals(Bot.getConfig().get("BLACK_JACK_GAME_ID"));
    }

    public static boolean isBotSpamChannel(MessageChannel channel) {
        return channel.getId().equals(Bot.getConfig().get("BOT_SPAM_ID"));
    }
    public static boolean isJokesChannel(MessageChannel channel) {
        return channel.getId().equals(Bot.getConfig().get("JOKES_ID"));
    }

}