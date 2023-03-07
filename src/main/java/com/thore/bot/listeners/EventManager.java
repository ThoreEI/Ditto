package com.thore.bot.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;


public class EventManager implements EventListener {
    private final static String BOT_SPAM_ID = "1065096620636643410";

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof GuildMemberJoinEvent)
            onGuildMemberJoin((GuildMemberJoinEvent) event);
        if (event instanceof MessageReceivedEvent)
            onMessageReceived((MessageReceivedEvent) event);
        if (event instanceof MessageReactionAddEvent)
            onMessageReactionAdd((MessageReactionAddEvent) event);
    }

    private static void onGuildMemberJoin(GuildMemberJoinEvent event) {
        MessageChannel channel =
                Objects.requireNonNull(
                        Objects.requireNonNull(event.getGuild().getDefaultChannel())
                                .asTextChannel());
        String newMember = Objects.requireNonNull(event.getUser()).getAsTag();
        channel.sendMessage("Welcome to the server " + newMember).queue();
    }

    private static void onMessageReactionAdd(MessageReactionAddEvent event) {
        String user = Objects.requireNonNull(event.getUser()).getName();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        MessageChannel channel = event.getChannel();
        String message = "Reaction from " + user + ": " + emoji;
        channel.sendMessage(message).queue();
    }

    private static void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getChannel().getId().equals(BOT_SPAM_ID))  // TODO
            return;
        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // TODO
    }
//                    AceDiamonds.png"
//                    AceHearts.png"
//                    AceSpades.png"
//                    CardDown.png"
//                    EightClubs.png"
//                    EightDiamonds.png"
//                    EightHearts.png"
//                    EightSpades.png"
//                    FiveClubs.png"
//                    FiveDiamonds.png"
//                    FiveHearts.png"
//                    FiveSpades.png"
//                    FourClubs.png"
//                    FourDiamonds.png"
//                    FourHearts.png"
//                    FourSpades.png"
//                    JackClubs.png"
//                    JackDiamonds.png"
//                    JackHearts.png"
//                    JackSpades.png"
//                    KingClubs.png"
//                    KingDiamonds.png"
//                    KingHearts.png"
//                    KingSpades.png"
//                    NineClubs.png"
//                    NineDiamonds.png"
//                    NineHearts.png"
//                    NineSpades.png"
//                    QueenClubs.png"
//                    QueenDiamonds.png"
//                    QueenHearts.png"
//                    QueenSpades.png"
//                    SevenClubs.png"
//                    SevenDiamonds.png"
//                    SevenHearts.png"
//                    SevenSpades.png"
//                    SixClubs.png"
//                    SixDiamonds.png"
//                    SixHearts.png"
//                    SixSpades.png"
//                    TenClubs.png"
//                    TenDiamonds.png"
//                    TenHearts.png"
//                    TenSpades.png"
//                    ThreeClubs.png"
//                    ThreeDiamonds.png"
//                    ThreeHearts.png"
//                    ThreeSpades.png"
//                    TwoClubs.png"
//                    TwoDiamonds.png"
//                    TwoHearts.png"
//                    TwoSpades.png"
//                    AceClubs.png"


}
