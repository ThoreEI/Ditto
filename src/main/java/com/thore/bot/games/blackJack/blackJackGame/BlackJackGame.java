package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.games.blackJack.domain.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackJackGame extends JPanel {
    private static Deck playingDeck;
    private static ArrayList<Player> players;
    private static Dealer dealer;
    private int wins, looses, pushes;
    private final static Scanner SCANNER_IN = new Scanner(System.in);

    public BlackJackGame() {
        buildDeck();
        createUsers();
        startRound();
    }

    private void buildDeck() {
        playingDeck = new Deck();
        playingDeck.shuffle();
    }

    private void createUsers() {
        players = new ArrayList<>();
        players.add(new Player("Thore")); // TODO Discord name
        dealer = new Dealer();
    }

    private void startRound() {
        // TODO condition
        for (int i = 0; i < 5; i++) {
            if (!(wins > 0 || looses > 0  || pushes > 0))
                System.out.println("Willkommen zum Black Jack!");
            for (Player player : players)
                placeBet(player);
            dealOutStarterHands();
            renderCards();
            checkForBlackJacks();
            for (Player player : players)
                makeDecision(player);
            checkForBusts();
        }
        SCANNER_IN.close();
    }

    private void checkForBlackJacks() {
        for (Player player : players) {
            if (!player.hasBlackJack() && !dealer.hasBlackJack())
                continue;
            if (player.hasBlackJack() && dealer.hasBlackJack()) {
                System.out.println("Unentschieden. " + player.getName() + " erhält seinen Einsatz zurück.");
                pushes++;
                player.chips += player.betAmount;
                player.betAmount = 0;
                startRound();
            } else if (player.hasBlackJack()) {
                System.out.println(player.getName() + " hat ein BlackJack! Gewinn: " + player.betAmount * 1.5);
                wins++;
                player.chips += player.betAmount * 1.5; // TODO round
                player.betAmount = 0;
                startRound();
            } else if (dealer.hasBlackJack()) {
                System.out.println(dealer.getName() + " hat ein BlackJack! " + player.getName() + " hat verloren.");
                looses++;
                player.betAmount = 0;
                startRound();
            }
        }
    }
    private void checkForBusts() {
        for (Player player : players)
            if (player.isBust()) {
                System.out.println(player.getName() + " hat sich überkauft und verliert.");
                looses++;
                player.betAmount = 0;
                startRound();
            }
    }

    // TODO buttons
    private void makeDecision(Player player) {
        System.out.println("1 --> Hit");
        System.out.println("2 --> Stand");
        System.out.println("3 --> Split");
        System.out.println("4 --> Double down");
        System.out.println("5 --> Surrender");
        int decision = SCANNER_IN.nextInt();
        switch (decision) {
            case 1 -> hit(player);
            case 2 -> stand(player);
            case 3 -> split(player);
            case 4 -> doubleDown(player);
            case 5 -> surrender(player);
        }
    }

    // requesting another card
    private void hit(Player currentPlayer) {
        boolean hasChosenHitAgain;
        do {
            System.out.println(currentPlayer.getName() + " erhält eine Karte.");
            currentPlayer.hand.drawCard(playingDeck);
            renderCards();
            checkForBlackJacks();
            checkForBusts();
            System.out.println("1 -> Eine weitere Karte?");
            System.out.println("2 -> Keine weitere Karte?");
            int selection = SCANNER_IN.nextInt();
            hasChosenHitAgain = selection == 1;
        } while (hasChosenHitAgain);
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

    // player gives up and gets half of chips back
    private void surrender(Player player) {
        System.out.println(player.getName() + " gibt auf. Die Hälfte des Einsatzes geht zurück.");
        looses++;
        player.chips += player.betAmount/2;
        player.betAmount=0;
    }

    // TODO dc-channel
    private void placeBet(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + " hat "  + currentPlayer.chips + " Chips.\nWie hoch ist dein Einsatz?");
        int betAmount = SCANNER_IN.nextInt();
        if (betAmount <= 0 || currentPlayer.chips < betAmount) {
            System.err.println("Du musst mindestens einen Chip und maximal " + currentPlayer.chips + " Chips setzen.");
            placeBet(currentPlayer);
        }
        currentPlayer.chips -=betAmount;
        currentPlayer.betAmount = betAmount;
    }

    private void dealOutStarterHands() {
        // Players are dealt two cards face up on the field.
        for (Player player : players) {
            player.hand.drawCard(playingDeck);
            player.hand.drawCard(playingDeck);
        }
        // The dealer also receives two cards. One is face down on the field.
        dealer.hand.drawCard(playingDeck);
        dealer.hand.drawCard(playingDeck);
    }

    public void renderCards() {
        for (Player player : players) {
            System.out.println(player.getName() + "'s Hand: " +  player.hand.calculateValue() + " Punkte.\n" + player.hand);
            for (int index = 0; index < player.hand.getNumberOfCards(); index++) {
                String rank = player.hand.getCard(index).getRank().toString();
                String suit = player.hand.getCard(index).getSuit().toString();
                String filename = rank + suit + ".png";
//                try {
//                    ImageMessenger.sendPngToTextChannel(filename);
//                } catch (IOException e) {
//                    throw new RuntimeException("An error occurred while trying to send the png image.");
//                }
            }
        }
    }

    @Override
    public String toString() {
        return "Punktestand: {" + "wins=" + wins + ", looses=" + looses + ", pushes=" + pushes + '}';
    }

    public static void main(String[] args) {
        new BlackJackGame();
    }
}