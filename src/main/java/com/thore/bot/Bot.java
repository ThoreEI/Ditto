package com.thore.bot;

import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import net.dv8tion.jda.api.JDA;

public class Bot {
    private static JDA JDA;

    public static void main(String[] args) throws InterruptedException {
        new BlackJackGame(2);
//        Dotenv config = Dotenv.configure().load();
//        JDABuilder builder = JDABuilder.createDefault(config.get("TOKEN"));
//        builder.setStatus(OnlineStatus.ONLINE);
//        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
//        builder.setActivity(Activity.watching("Everything"));
//        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT);
//        builder.addEventListeners(
//                new EventManager(),
//                new CommandListener(),
//                new VoiceListener());
//        JDA = builder.build();
//        JDA.awaitReady();
    }

    public static JDA getJDA() {
        return JDA;
    }
}