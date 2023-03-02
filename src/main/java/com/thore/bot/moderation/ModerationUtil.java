package com.thore.bot.moderation;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.concurrent.TimeUnit;

public class ModerationUtil {
    public static void deleteMessage(Message message, String reason) {
        message.delete().reason(reason).queue();
    }

        public static void ban(Guild guild, User user, String reason) {
            guild.ban(user, 7, TimeUnit.valueOf(reason)).queue();
        }



}