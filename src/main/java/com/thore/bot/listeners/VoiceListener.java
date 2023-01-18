package com.thore.bot.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VoiceListener extends ListenerAdapter {
    private static List<Long> voiceChannels;

    public VoiceListener() {
        voiceChannels = new ArrayList<>();
    }

    final static CacheFlag CACHE_FLAG = CacheFlag.ACTIVITY;
//    public void onGuildVoiceJoin(@NotNull GuildJoinEvent event) {
//        onJoin(event.getGuild().getN
//    }

//    @Override
//    public void onGuildLeave(GuildLeaveEvent event) {
//        super.onGuildLeave(event);
//    }

    public void onJoin(@NotNull VoiceChannel joined, Member member) {
        if(joined.getIdLong() == 576016415597789194L) {
            Category category = joined.getParentCategory();
            if (category == null) throw new AssertionError();
            VoiceChannel voiceChannel = category.createVoiceChannel("⏳ | " + member.getEffectiveName()).complete();
            voiceChannel.getManager().setUserLimit(joined.getUserLimit()).queue();
            voiceChannel.getGuild().moveVoiceMember(member, voiceChannel).queue();
            this.voiceChannels.add(voiceChannel.getIdLong());
        }
    }

    public void onLeave(@NotNull VoiceChannel channel) {
        if(channel.getMembers().size() <= 0) {
            if(this.voiceChannels.contains(channel.getIdLong())) {
                this.voiceChannels.remove(channel.getIdLong());
                channel.delete().queue();
            }
        }
    }
}