package com.thore.bot.listeners;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import com.thore.bot.io.reader.FileReader;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {
    public final static String BOT_SPAM_CHANNEL = Bot.getConfig().get("BOT_SPAM_ID");

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
      if (BOT_SPAM_CHANNEL.equals(event.getChannel().getId()))
            return;
        if (event.getName().equals("joke")) {
            event.reply(FileReader.loadJoke()).queue();
        } else if (event.getName().equals("blackjack")) {
            event.reply("Blackjack is starting...").queue();
            new BlackJackGame();
        }
    }
}
