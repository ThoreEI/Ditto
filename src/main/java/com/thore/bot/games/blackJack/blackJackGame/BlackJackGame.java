package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.domain.*;
import com.thore.bot.io.reader.FileReader;
import com.thore.bot.listeners.ButtonListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.thore.bot.IDHandler.getBlackJackGameChannel;

public class BlackJackGame {
    public static ArrayList<Player> players;
    private static Deck deck, discardPile;
    private static Dealer dealer;
    private static EmbedBuilder betFrame, decisionFrame, scoreFrame, cardFrame, resultFrame;
    private static int roundCounter;


    public BlackJackGame(User... players) {
        deck = new Deck();
        discardPile = new Deck();
        dealer = new Dealer();
        BlackJackGame.players = new ArrayList<>();
        for (User player : players)
            createUser(player.getName());
        roundCounter = 0;
        startRound();
    }

    private static void createUser(String username) {
        players.add(new Player(username));
    }

    public void startRound() {
        if (roundCounter > 0)
            shuffleHandsIntoDiscardPile();
        if (deck.getDeck().size() < players.size())
            deck.replenishWithCards(discardPile);


        // TODO concurrentFuture
        for (Player player : players) {
            selectBet(player);
            dealOutPlayersHand(player);
        }
        dealOutDealersHand();
        //while (true) {
        for (Player player : players)
            renderCards(player);
        checkForBlackJacks();
        for (Player player : players)
            makeDecision(player);
        checkForBusts();
    }
    
    // Players are dealt two cards face up on the field.
    private void dealOutPlayersHand(Player player) {
        player.hand.drawCard(deck);
        player.hand.drawCard(deck);
    }

    // The dealer also receives two cards. One is face down on the field.
    private void dealOutDealersHand() {
        dealer.hand.drawCard(deck);
        dealer.hand.drawCard(deck);
    }

    private void shuffleHandsIntoDiscardPile() {
        for (Player player : players)
            discardPile.addCards(player.hand.getCards());
    }

    private void checkForBlackJacks() {
        for (Player player : players) {
            if (!player.hasBlackJack() && !dealer.hasBlackJack())
                return;
            else if (player.hasBlackJack()) {
                player.chips += player.betAmount * 1.5;
                getBlackJackGameChannel().sendMessage(player.getName() + " hat ein BlackJack! Gewinn: " + player.betAmount * 1.5).queue();
                player.incrementWins();
            } else if (player.hasBlackJack() && dealer.hasBlackJack()) {
                player.chips += player.betAmount;
                getBlackJackGameChannel().sendMessage("Unentschieden. " + player.getName() + " erhält seinen Einsatz zurück.").queue();
                player.incrementPushes();
            } else if (dealer.hasBlackJack()) {
                getBlackJackGameChannel().sendMessage(dealer.getName() + " hat ein BlackJack! " + player.getName() + " hat verloren.").queue();
                player.incrementLooses();
            }
            player.betAmount = 0;
            //startRound(); TODO
        }
    }

    private void checkForBusts() {
        for (Player player : players)
            if (player.isBust()) {
                getBlackJackGameChannel().sendMessage(player.getName() + " hat sich überkauft und verliert.").queue();
                player.incrementLooses();
                player.betAmount = 0;
                //  startRound(); TODO
            }
    }

    // TODO buttons
    private void makeDecision(Player player) {
        decisionFrame = new EmbedBuilder();
        decisionFrame.setTitle(player.getName() + " hat die Qual der Wahl.")
                .setDescription("Triff jetzt eine Entscheidung!")
                .setColor(Color.GREEN);

        Button hitButton = Button.primary("hitButton", "Hit");
        Button standButton = Button.primary("standButton", "Stand");
        Button splitButton = Button.primary("splitButton", "Split");
        Button doubleDownButton = Button.primary("doubleDownButton", "Double down");
        Button surrenderButton = Button.primary("surrenderButton", "Surrender");
        List<ItemComponent> row1 = new ArrayList<>(List.of(new Button[]{hitButton, standButton, splitButton}));
        List<ItemComponent> row2 = new ArrayList<>(List.of(new Button[]{doubleDownButton, surrenderButton}));
        getBlackJackGameChannel()
                .sendMessageEmbeds(decisionFrame.build())
                .addActionRow(row1).addActionRow(row2)
                .queue(message -> {
                    message.addReaction(Emoji.fromFormatted(":boom:")).queue();
                    message.addReaction(Emoji.fromFormatted(":stop_sign:")).queue();
                    message.addReaction(Emoji.fromFormatted(":scissors:")).queue();
                    message.addReaction(Emoji.fromFormatted(":repeat:")).queue();
                    message.addReaction(Emoji.fromFormatted(":white_flag")).queue();
                    Bot.getJda().addEventListener(new ButtonListener(player));
                });
    }

    // requesting another card
    private void hit(Player currentPlayer) {
        boolean hasChosenHitAgain;
        do {
            getBlackJackGameChannel().sendMessage(currentPlayer.getName() + " erhält eine Karte.").queue();
            currentPlayer.hand.drawCard(deck);
            renderCards(currentPlayer);
            checkForBlackJacks();
            checkForBusts();
            getBlackJackGameChannel().sendMessage("1 -> Eine weitere Karte?").queue();
            getBlackJackGameChannel().sendMessage("2 -> Keine weitere Karte?").queue();
            int selection =1; // TODO
            hasChosenHitAgain = selection == 1;
        } while (hasChosenHitAgain);
    }

    // don't ask for a card
    private void stand(Player currentPlayer) {
        getBlackJackGameChannel().sendMessage(currentPlayer.getName() + " möchte keine weitere Karte.").queue();
    }

    // if you're dealt a pair (2 cards of equal value) you can split the cards.
    private void split(Player currentPlayer) {
        if (!currentPlayer.hand.isPair()) {
            getBlackJackGameChannel().sendMessage("Nur mit einem Paar ist ein Split möglich.").queue();
            return;
        }
        // Each card becomes the first card on two new hands. A second bet becomes possible.
        Hand currentHand = currentPlayer.hand;
        currentPlayer.hand = new Hand(
                currentPlayer.hand.getCard(0),
                deck.drawCard()
        );
        currentPlayer.secondHand = new Hand(
                currentHand.getCard(1),
                deck.drawCard()
        );
        // TODO bet is possible
    }

    private void doubleDown(Player player) {
        System.out.println(" ");
        // TODO
    }

    // player gives up and gets half of chips back
    private void surrender(Player player) {
        getBlackJackGameChannel().sendMessage(player.getName() + " gibt auf. Die Hälfte des Einsatzes geht zurück.").queue();
        player.incrementLooses();
        player.chips += player.betAmount/2;
        player.betAmount=0;
    }

    // TODO dc-channel
    private void selectBet(Player player) {
        betFrame = new EmbedBuilder();
        betFrame.setTitle("Auswahl der Einsätze");
        betFrame.setDescription("Du kannst 150% des Einsatzes gewinnen, aber auch alles verlieren.");
        betFrame.addField(player.getName(), " ist an der Reihe.", false);
        betFrame.addField("Verfügbare Chips: " + player.chips,"Wähle deinen Einsatz.", false);
        ArrayList<ItemComponent> row1 = new ArrayList<>();
        ArrayList<ItemComponent> row2 = new ArrayList<>();
        for (int coinValue : new int[] { 1, 5, 10, 25, 50})
            row1.add(Button.primary("btnChip" + coinValue, String.valueOf(coinValue)));
        for (int coinValue : new int[] {100, 200, 500, 1000, 5000})
            row2.add(Button.danger("btnChip" + coinValue, String.valueOf(coinValue)));
        getBlackJackGameChannel().sendMessageEmbeds(betFrame.build())
                .addActionRow(row1).addActionRow(row2).queue(message -> Bot.getJda().addEventListener(new ButtonListener(player)));
    }

    public void renderCards(Player player) {
        betFrame = new EmbedBuilder();
        betFrame.setTitle(player.getName() + "'s Hand");
        betFrame.setDescription("Punkte: "+ player.hand.calculateValue());
        for (int index = 0; index < player.hand.getCards().size(); index++) {
            String cardDescription = player.hand.getCard(index).getCardDescription();
            File pngFile = FileReader.loadCard(cardDescription); //getPathOfCard(cardDescription);
            getBlackJackGameChannel().sendMessageEmbeds(betFrame.build())
                    .addFiles(FileUpload.fromData(pngFile))
                    .queue();
        }
    }
}