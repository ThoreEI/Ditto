package com.thore.bot.listeners;
import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import com.thore.bot.games.blackJack.domain.Player;
import com.thore.bot.io.reader.FileReader;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.thore.bot.listeners.EventManager.*;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (isBotSpamChannel(event.getChannel()))
            return;
        else if (event.getName().equals("joke") && isJokesChannel(event.getChannel()))
            event.reply(FileReader.loadJoke()).queue();

        else if (event.getName().equals("blackjack") && isBlackJackChannel(event.getChannel())) {
//            Category blackJackCategory = Objects.requireNonNull(event.getGuild())
//                    .getCategoriesByName("blackjack", true)
//                    .get(0);
//            TextChannel temporaryChannel = blackJackCategory.createTextChannel("Black Jack Game " + blackJackCategory.getChannels().size())
//                    .setTopic("Das ist ein temporärer Black Jack Channel.")
//                    .complete();
//                    new BlackJackGame(temporaryChannel);
        } else if (event.getName().equals("join") && isBlackJackChannel(event.getChannel())) {  // TODO child channel
            String username = event.getUser().getName();
            BlackJackGame.players.add(new Player(username));
            event.getChannel().sendMessage(username + " wurde der Spielerliste hinzugefügt.").queue();
        }  else if (event.getName().equals("start") && isBlackJackChannel(event.getChannel())) {
            event.reply("Blackjack is starting...").queue();
        }
        System.out.println();
    }
}
