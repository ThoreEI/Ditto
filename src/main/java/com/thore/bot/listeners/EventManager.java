package com.thore.bot.listeners;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
public class EventManager implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof GuildMemberJoinEvent) {
            new StateListener().onGuildMemberJoin((GuildMemberJoinEvent) event);
        }
        if (event instanceof MessageReceivedEvent) {
            new MessageListener().onMessageReceived((MessageReceivedEvent) event);
        }
        if (event instanceof MessageReactionAddEvent) {
            new ReactionListener().onMessageReactionAdd((MessageReactionAddEvent) event);
        }
    }
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