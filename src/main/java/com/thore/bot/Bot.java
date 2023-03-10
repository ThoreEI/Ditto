package com.thore.bot;
import com.thore.bot.listeners.*;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {
    private static JDA jda;
    private static Dotenv config;

    public static JDA getJda() {
        return jda;
    }
    public static Dotenv getConfig () {
        return config;
    }

    public static void main(String[] args) {
        config = Dotenv.configure().load();
        JDABuilder builder = JDABuilder.createDefault(config.get("TOKEN"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("with you."));
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(
                new CommandListener(),
                new MessageListener(),
                new StateListener(),
                new ReactionListener());
        jda = builder.build();
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            System.err.println("The API couldn't connect to the server.");
        }
    }

}