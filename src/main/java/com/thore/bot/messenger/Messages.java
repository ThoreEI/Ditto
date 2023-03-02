package com.thore.bot.messenger;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class Messages {

    public static void sendMessage(@NotNull MessageChannel channel, String message) {
        channel.sendMessage(message).queue();
    }

    public void sendPrivateMessage(@NotNull User user, String content) {
        user.openPrivateChannel().queue((channel) -> channel.sendMessage(content).queue());
    }

    public void sendAndLog(@NotNull MessageChannel channel, String message) {
        channel.sendMessage(message).queue(msg -> System.out.printf("Sent Message %s\n", msg));
    }

}

