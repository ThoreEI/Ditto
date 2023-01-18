package com.thore.bot;

import com.thore.bot.listeners.CommandListener;
import com.thore.bot.listeners.EventListener;
import com.thore.bot.listeners.VoiceListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
public class Bot {
    public final static Dotenv CONFIG = Dotenv.configure().load();
    public final static DefaultShardManagerBuilder BUILDER = DefaultShardManagerBuilder.createDefault(CONFIG.get("TOKEN"));
    public final static ShardManager SHARD_MANAGER = BUILDER.build();

    private static void initializeBot() {
        SHARD_MANAGER.addEventListener(new CommandListener(), new EventListener(), new VoiceListener());
        BUILDER.setStatus(OnlineStatus.ONLINE)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setActivity(Activity.watching("I'M WATCHING YOU"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);
    }

    public static void main(String[] args) {
        initializeBot();
    }
}
