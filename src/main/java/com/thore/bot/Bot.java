package com.thore.bot;

import com.thore.bot.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {
    private final Dotenv config;
    private final ShardManager shardManager;
    public Bot() {
        config = Dotenv.configure().load();
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.get("TOKEN"));
        shardManager = builder.build();
        shardManager.addEventListener(new EventListener(), new EventListener());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("you....."));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
    }

    public Dotenv getConfig() {
        return config;
    }
    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
      new Bot();
    }
}
