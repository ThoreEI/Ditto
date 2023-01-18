package com.thore.bot.cleaner;

import com.thore.bot.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageGarbageCollector {

public MessageGarbageCollector() {
    for (TextChannel channel : Bot.SHARD_MANAGER.getTextChannels())
        checkChannel(channel);
    }

    public static void checkChannel(@NotNull TextChannel textChannel) {
        List<Message> messages = textChannel.getHistory().retrievePast(50).complete();
        textChannel.deleteMessages(messages).queue();
        System.out.println("Deleted"); // TODO
    }
}

