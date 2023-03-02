package com.thore.bot.cleaner;

import com.thore.bot.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.util.List;

public class ChannelMessageCleaner {

    public static void checkTextChannel() {
        List<Message> messages;
        for (TextChannel channel : Bot.getJDA().getTextChannels()) {
            messages = channel.getHistory().retrievePast(50).complete();
            channel.deleteMessages(messages).queue();
        }
    }
}

