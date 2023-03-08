package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.games.blackJack.domain.*;
import com.thore.bot.io.messenger.ImageMessenger;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackJackGame extends JPanel {
    private static Deck playingDeck;
    private static Deck discardedDeck;
    private static ArrayList<Player> players;
    private static Dealer dealer;
    private static int lapCounter;

    private final static Scanner SCANNER_IN = new Scanner(System.in);

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
            System.out.println("Die " + (lapCounter + 1) + " Runde beginnt.");
            placeBets();
            dealOutStarterHands();
            renderCards();
            if(isBlackJack())
                continue;
            if (isBust())
                System.out.println("Noch " + players.size() + " Spieler übrig.");
            makeDecision();
            lapCounter++;
        }
    }

    // player/dealer has a total card value of 21
    private boolean isBlackJack() {
        for (Player currentPlayer : players)
            if (currentPlayer.hasBlackJack()) {
                System.out.println("BlackJack! " + currentPlayer.getName());
                return true;
            }
        return false;
    }

    // a bust occurs when a player has more than 21 points
    private boolean isBust() {
        for (Player currentPlayer : players)
            if (currentPlayer.hand.calculatePoints() > 21) {
                System.out.println("Bust! " + currentPlayer.getName());
                players.remove(currentPlayer); // TODO empty slot list
                return true;
            }
        return false;
    }

    // TODO buttons
    private void makeDecision() {
        for (Player currentPlayer : players) {
            System.out.println("1 --> Hit");
            System.out.println("2 --> stand");
            System.out.println("3 --> split");
            System.out.println("4 --> double down");
            int decision = SCANNER_IN.nextInt();
            switch (decision) {
                case 1 -> hit(currentPlayer);
                case 2 -> stand(currentPlayer);
                case 3 -> split(currentPlayer);
                case 4 -> doubleDown(currentPlayer);
            }
        }
    }



    // requesting another card
    private void hit(Player currentPlayer) {
        boolean playerWantsToHit = true;
        while (!isBust() && playerWantsToHit) {
            System.out.println("1 -> Eine weitere Karte?");
            System.out.println("2 -> " + "Keine weitere Karte?");
            if (SCANNER_IN.nextInt() == 2) // TODO check and channel
                playerWantsToHit = false;
            currentPlayer.hand.drawCard(playingDeck);
        }
        System.out.println(currentPlayer.getName() + " erhält eine weitere Karte.");
    }

    // don't ask for a card
    private void stand(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + " möchte keine weitere Karte.");
    }

    // if you're dealt a pair (2 cards of equal value) you can split the cards.
    private void split(Player currentPlayer) {
        if (!currentPlayer.hand.isPair()) {
            System.out.println("Nur mit einem Paar ist ein Split möglich.");
            return;
        }
        // Each card becomes the first card on two new hands. A second bet becomes possible.
        Card card1 = currentPlayer.hand.getCard(0);
        Card card2 = currentPlayer.hand.drawCard(playingDeck);
        Card card3 = currentPlayer.hand.getCard(1);
        Card card4 = currentPlayer.hand.drawCard(playingDeck);
        currentPlayer.hand = new Hand(card1, card2);
        currentPlayer.secondHand = new Hand(card3, card4);
        // TODO bet is possible
    }

    private void doubleDown(Player currentPlayer) {
    }

    // TODO dc-channel
    private void placeBets() {
        for (Player currentPlayer : players) {
            System.out.println("Einsatz zwischen 0 - 1000 Euro.");
            System.out.println("Wie hoch ist der Einsatz?");
            int betAmount  = Integer.parseInt(SCANNER_IN.nextLine());
            if (currentPlayer.money >= betAmount && 0 < betAmount && betAmount < 1000)
                currentPlayer.money = betAmount;
            else
                placeBets();
        }
    }

    private void dealOutStarterHands() {
        // Players are dealt two cards face up on the field.
        for (Player currentPlayer : players) {
            currentPlayer.hand.drawCard(playingDeck);
            currentPlayer.hand.drawCard(playingDeck);
        }
        // The dealer also receives two cards. One is face down on the field.
        dealer.hand.drawCard(playingDeck);
        dealer.hand.drawCard(playingDeck);
    }



    public void renderCards() {
        for (Player player : players) {
            String name = player.getName();
            Hand hand = player.hand;
            int value = hand.calculatePoints();
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