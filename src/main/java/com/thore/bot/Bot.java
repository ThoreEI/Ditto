package com.thore.bot;

import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import com.thore.bot.listeners.CommandListener;
import com.thore.bot.listeners.EventManager;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {
    private static JDA JDA;
    private static Dotenv CONFIG;

    public static void main(String[] args) throws InterruptedException {
        CONFIG = Dotenv.configure().load();
        JDABuilder builder = JDABuilder.createDefault(CONFIG.get("TOKEN"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setActivity(Activity.watching("Everything"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(new EventManager(), new CommandListener());
        JDA = builder.build();
        JDA.awaitReady();
        new BlackJackGame();
    }

    public static JDA getJDA() {
        return JDA;
    }

    public static Dotenv getConfig () {
        return CONFIG;
    }
}