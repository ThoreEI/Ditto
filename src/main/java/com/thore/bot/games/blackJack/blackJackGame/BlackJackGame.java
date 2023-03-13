package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.domain.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;

public class BlackJackGame {
    public static ArrayList<Player> players;
    private TextChannel gameChannel;
    private static Deck playingDeck;
    private static Dealer dealer;
    private int wins, looses, pushes;

    public BlackJackGame(/*TextChannel textChannel*/) {
        String blackJackGame = Bot.getConfig().get("BLACK_JACK_GAME_ID");
        buildDeck();
        createUser();
    }

    private void buildDeck() {
        playingDeck = new Deck();
        playingDeck.shuffle();
    }

    private void createUser() {
        BlackJackGame.players = new ArrayList<>();
        dealer = new Dealer();
    }

    public void startRound() {
        // TODO condition
        while (true) {
            if (wins == 0 && looses == 0  && pushes == 0)
                gameChannel.sendMessage("Willkommen zum Black Jack!").queue();
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
            if (player.hasBlackJack() && dealer.hasBlackJack()) {
                gameChannel.sendMessage("Unentschieden. " + player.getName() + " erhält seinen Einsatz zurück.").queue();
                pushes++;
                player.chips += player.hand.betAmount;
            } else if (player.hasBlackJack()) {
                gameChannel.sendMessage(player.getName() + " hat ein BlackJack! Gewinn: " + player.hand.betAmount * 1.5).queue();
                wins++;
                player.chips += player.hand.betAmount * 1.5;
            } else if (dealer.hasBlackJack()) {
                gameChannel.sendMessage(dealer.getName() + " hat ein BlackJack! " + player.getName() + " hat verloren.").queue();
                looses++;
            }
            player.betAmount = 0;
            startRound();
        }
    }
    private void checkForBusts() {
        for (Player player : players)
            if (player.isBust()) {
                gameChannel.sendMessage(player.getName() + " hat sich überkauft und verliert.").queue();
                looses++;
                player.betAmount = 0;
                startRound();
            }
    }

    // TODO buttons
    private void makeDecision(Player player) {
        gameChannel.sendMessage("""
                1 --> Hit
                2 --> Stand
                3 --> Split
                4 --> Double down
                5 --> Surrender""").queue();
        int decision = 2;  // TODO
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
            gameChannel.sendMessage(currentPlayer.getName() + " erhält eine Karte.").queue();
            currentPlayer.hand.drawCard(playingDeck);
            renderCards();
            checkForBlackJacks();
            checkForBusts();
            gameChannel.sendMessage("1 -> Eine weitere Karte?").queue();
            gameChannel.sendMessage("2 -> Keine weitere Karte?").queue();
            int selection =1; // TODO
            hasChosenHitAgain = selection == 1;
        } while (hasChosenHitAgain);
    }

    // don't ask for a card
    private void stand(Player currentPlayer) {
        gameChannel.sendMessage(currentPlayer.getName() + " möchte keine weitere Karte.").queue();
    }

    // if you're dealt a pair (2 cards of equal value) you can split the cards.
    private void split(Player currentPlayer) {
        if (!currentPlayer.hand.isPair()) {
            gameChannel.sendMessage("Nur mit einem Paar ist ein Split möglich.").queue();
            return;
        }
        // Each card becomes the first card on two new hands. A second bet becomes possible.
        Hand currentHand = currentPlayer.hand;
        currentPlayer.hand = new Hand(
                currentPlayer.hand.getCard(0),
                playingDeck.drawCard()
        );
        currentPlayer.secondHand = new Hand(
                currentHand.getCard(1),
                playingDeck.drawCard()
        );
        // TODO bet is possible
    }

    private void doubleDown(Player currentPlayer) {
        // TODO
    }

    // player gives up and gets half of chips back
    private void surrender(Player player) {
        gameChannel.sendMessage(player.getName() + " gibt auf. Die Hälfte des Einsatzes geht zurück.").queue();
        looses++;
        player.chips += player.betAmount/2;
        player.betAmount=0;
    }

    // TODO dc-channel
    private void placeBet(Player currentPlayer) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Auswahl der Einsätze");
        embedBuilder.addField(currentPlayer.getName(), " ist an der Reihe.", false);
        embedBuilder.addField("Verfügbare Chips: " + currentPlayer.chips,"Wähle deinen Einsatz.", false);
        ArrayList<ItemComponent> firstRowOfChips = new ArrayList<>();
        ArrayList<ItemComponent> secondRowOfChips= new ArrayList<>();
        for (int coinValue : new int[] { 1, 5, 10, 25, 50})
            firstRowOfChips.add(Button.primary("btnChip" + coinValue, String.valueOf(coinValue)));
        for (int coinValue : new int[] {100, 200, 500, 1000, 5000})
            secondRowOfChips.add(Button.danger("btnChip" + coinValue, String.valueOf(coinValue)));

        gameChannel.sendMessageEmbeds(embedBuilder.build())
                .addActionRow(firstRowOfChips)
                .addActionRow(secondRowOfChips).queue();
        int betAmount = 1;
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
            //BLACK_JACK_GAME_CHANNEL.sendMessage(name + "'s Hand: " +  value + " Punkte.\n" + player.hand).queue();
            for (int index = 0; index < player.hand.getNumberOfCards(); index++) {
                String rank = player.hand.getCard(index).getRank().toString();
                String suit = player.hand.getCard(index).getSuit().toString();
                String filename = rank + suit + ".png";
                // gameChannel.sendMessage("").addFiles(new FileUpload()filename)
            }
        }
    }

    @Override
    public String toString() {
        return "Punktestand: {" + "wins=" + wins + ", looses=" + looses + ", pushes=" + pushes + '}';
    }
}