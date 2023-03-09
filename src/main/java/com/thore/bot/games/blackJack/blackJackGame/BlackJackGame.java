package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.domain.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackJackGame {
    private final static TextChannel BLACK_JACK_GAME_CHANNEL = Bot.getJda().getTextChannelById(Bot.getConfig().get("BLACK_JACK_GAME_ID"));
    private static Deck playingDeck;
    private static ArrayList<Player> players;
    private static Dealer dealer;
    private int wins, looses, pushes;
    private final static Scanner SCANNER_IN = new Scanner(System.in);

    public BlackJackGame() {
        buildDeck();
        createUsers();
        startRound();
        SCANNER_IN.close();
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
        while (true) {
            if (!(wins > 0 || looses > 0  || pushes > 0)) {
                String blackJackGameID = Bot.getConfig().get("BLACK_JACK_GAME_ID");
                TextChannel blackJackGame = Bot.getJda().getTextChannelById(blackJackGameID);
                assert blackJackGame != null; // TODO other solution
                BLACK_JACK_GAME_CHANNEL.sendMessage("Willkommen zum Black Jack!").queue();
            }

            for (Player player : players)
                placeBet(player);
            dealOutStarterHands();
            renderCards();
            checkForBlackJacks();
            for (Player player : players)
                makeDecision(player);
            checkForBusts();
        }
    }

    private void checkForBlackJacks() {
        for (Player player : players) {
            if (!player.hasBlackJack() && !dealer.hasBlackJack())
                continue;
            if (player.hasBlackJack() && dealer.hasBlackJack()) {
                BLACK_JACK_GAME_CHANNEL.sendMessage("Unentschieden. " + player.getName() + " erhält seinen Einsatz zurück.").queue();
                pushes++;
                player.chips += player.betAmount;
                player.betAmount = 0;
                startRound();
            } else if (player.hasBlackJack()) {
                BLACK_JACK_GAME_CHANNEL.sendMessage(player.getName() + " hat ein BlackJack! Gewinn: " + player.betAmount * 1.5).queue();
                wins++;
                player.chips += player.betAmount * 1.5; // TODO round
                player.betAmount = 0;
                startRound();
            } else if (dealer.hasBlackJack()) {
                BLACK_JACK_GAME_CHANNEL.sendMessage(dealer.getName() + " hat ein BlackJack! " + player.getName() + " hat verloren.").queue();
                looses++;
                player.betAmount = 0;
                startRound();
            }
        }
    }
    private void checkForBusts() {
        for (Player player : players)
            if (player.isBust()) {
                BLACK_JACK_GAME_CHANNEL.sendMessage(player.getName() + " hat sich überkauft und verliert.").queue();
                looses++;
                player.betAmount = 0;
                startRound();
            }
    }

    // TODO buttons
    private void makeDecision(Player player) {
        BLACK_JACK_GAME_CHANNEL.sendMessage("1 --> Hit").queue();
        BLACK_JACK_GAME_CHANNEL.sendMessage("2 --> Stand").queue();
        BLACK_JACK_GAME_CHANNEL.sendMessage("3 --> Split").queue();
        BLACK_JACK_GAME_CHANNEL.sendMessage("4 --> Double down").queue();
        BLACK_JACK_GAME_CHANNEL.sendMessage("5 --> Surrender").queue();
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
            BLACK_JACK_GAME_CHANNEL.sendMessage(currentPlayer.getName() + " erhält eine Karte.").queue();
            currentPlayer.hand.drawCard(playingDeck);
            renderCards();
            checkForBlackJacks();
            checkForBusts();
            BLACK_JACK_GAME_CHANNEL.sendMessage("1 -> Eine weitere Karte?").queue();
            BLACK_JACK_GAME_CHANNEL.sendMessage("2 -> Keine weitere Karte?").queue();
            int selection = SCANNER_IN.nextInt();
            hasChosenHitAgain = selection == 1;
        } while (hasChosenHitAgain);
    }

    // don't ask for a card
    private void stand(Player currentPlayer) {
        BLACK_JACK_GAME_CHANNEL.sendMessage(currentPlayer.getName() + " möchte keine weitere Karte.").queue();
    }

    // if you're dealt a pair (2 cards of equal value) you can split the cards.
    private void split(Player currentPlayer) {
        if (!currentPlayer.hand.isPair()) {
            BLACK_JACK_GAME_CHANNEL.sendMessage("Nur mit einem Paar ist ein Split möglich.").queue();
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
        // TODO
    }

    // player gives up and gets half of chips back
    private void surrender(Player player) {
        BLACK_JACK_GAME_CHANNEL.sendMessage(player.getName() + " gibt auf. Die Hälfte des Einsatzes geht zurück.").queue();
        looses++;
        player.chips += player.betAmount/2;
        player.betAmount=0;
    }

    // TODO dc-channel
    private void placeBet(Player currentPlayer) {
        BLACK_JACK_GAME_CHANNEL.sendMessage(currentPlayer.getName() + " hat "  + currentPlayer.chips + " Chips.\nWie hoch ist dein Einsatz?").queue();
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
            String name = player.getName();
            int value = player.hand.calculateValue();
            BLACK_JACK_GAME_CHANNEL.sendMessage(name + "'s Hand: " +  value + " Punkte.\n" + player.hand).queue();
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

    public static TextChannel getBlackJackGameChannel () {
        return BLACK_JACK_GAME_CHANNEL;
    }
}