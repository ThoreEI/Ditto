package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.games.blackJack.domain.*;
import com.thore.bot.io.messenger.ImageMessenger;
import com.thore.bot.io.reader.FileReader;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class BlackJackGame extends JPanel {
    private static Deck playingDeck;
    private static Deck discardedDeck;
    private static ArrayList<Player> players;
    private static Dealer dealer;
    private static int lapCounter;

    public BlackJackGame() {
        buildDecks();
        createUsers();
        startRound();
    }



    private void buildDecks() {
        playingDeck = new Deck();
        discardedDeck = new Deck();
        playingDeck.shuffle();
    }

    private void createUsers() {
        players = new ArrayList<>();
        players.add(new Player("Thore"));
        dealer = new Dealer();
    }

    private void startRound() {
        lapCounter = 0;
        while (true) {
            if (lapCounter == 0)
                System.out.println("Willkommen");
            System.out.println("Die " + (lapCounter + 1) + " Runde beginnt");
            dealCards();
            renderCards();
            lapCounter++;
        }
    }

    private void dealCards() {
        for (Player currentPlayer : players) {
            currentPlayer.getHand().drawCard(playingDeck);
            currentPlayer.getHand().drawCard(playingDeck);
        }
        dealer.getHand().drawCard(playingDeck);
        dealer.getHand().drawCard(playingDeck);
    }

    public void renderCards() {
        for (Player player : players) {
            String name = player.getName();
            Hand hand = player.getHand();
            int value = hand.calculateValue();
            // System.out.println(name + " Hand:" + hand + "Value: " + value);
            for (int position = 0; position < hand.getNumberOfCards(); position++) {
                String rank = hand.getCard(position).getRank().toString();
                String suit = hand.getCard(position).getSuit().toString();
                String filename = rank + suit + ".png";
                try {
                    ImageMessenger.sendPngToTextChannel(filename);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}