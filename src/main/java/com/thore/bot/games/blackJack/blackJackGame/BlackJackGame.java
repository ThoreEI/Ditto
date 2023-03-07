package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.domain.*;
import com.thore.bot.io.reader.FileReader;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class BlackJackGame extends JPanel {
    private static Deck playingDeck;
    private static Deck discardedDeck;
    private static ArrayList<Player> players;
    private static Dealer dealer;
    private static int lapCounter;

    public BlackJackGame() {
        lapCounter=0;
        buildDecks();
        players = new ArrayList<>();
        players.add(new Player("Thore")); // TODO Mehrspieler
        startRound();
    }

    private void buildDecks() {
        playingDeck = new Deck();
        discardedDeck = new Deck();
        playingDeck.shuffle();
    }

    private void startRound() {
        // TODO condition für while true
        while (lapCounter < 10) {
            if (lapCounter == 0)
                System.out.println("Willkommen");
            System.out.println("Die " + (lapCounter + 1) + " Runde beginnt");
            renderCards();
            lapCounter++;
        }
        players.get(0).getHand().drawCardFromDeck(playingDeck); // TODO für Mehrspieler konzipieren
    }

    public void renderCards() {
        for (Player player : players) {
            String name = player.getName();
            Hand hand = player.getHand();
            int value = hand.calculateValue();
            System.out.println(name + " Hand:" + hand + "Value: " + value);
            for (int position = 0; position < hand.getNumberOfCards(); position++) {
                String rank = hand.getCard(position).getRank().toString();
                String suit = hand.getCard(position).getSuit().toString();
                String filename = rank + suit + ".png";
                System.out.println(filename);
                try {
                  Image image = FileReader.loadCard(filename);
                    // TODO in Chat posten
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }
}