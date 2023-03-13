package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.games.blackJack.domain.*;
import com.thore.bot.io.reader.FileReader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import java.io.File;
import java.util.ArrayList;
import static com.thore.bot.IDHandler.getBlackJackGameChannel;

public class BlackJackGame {
    public static ArrayList<Player> listOfPlayers;
    private static Deck playingDeck;
    private static Dealer dealer;

    public BlackJackGame(User... players) {
        dealer = new Dealer();
        playingDeck = new Deck();
        listOfPlayers = new ArrayList<>();
        for (User user : players)
            createUser(user.getName());
        startRound();
    }

    private void createUser(String username) {
        listOfPlayers.add(new Player(username));
    }

    public void startRound() {
        for (Player player : listOfPlayers) {
            placeBet(player);
            dealOutPlayersHand(player);
        }
        dealOutDealersHand();
        //while (true) {
        for (Player player : listOfPlayers)
            renderCards(player);
        checkForBlackJacks();
        for (Player player : listOfPlayers)
            makeDecision(player);
        checkForBusts();
        // }
    }

    // Players are dealt two cards face up on the field.
    private void dealOutPlayersHand(Player player) {
        player.hand.drawCard(playingDeck);
        player.hand.drawCard(playingDeck);
    }

    // The dealer also receives two cards. One is face down on the field.
    private void dealOutDealersHand() {
        dealer.hand.drawCard(playingDeck);
        dealer.hand.drawCard(playingDeck);
    }

    private void checkForBlackJacks() {
        for (Player player : listOfPlayers) {
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
        for (Player player : listOfPlayers)
            if (player.isBust()) {
                getBlackJackGameChannel().sendMessage(player.getName() + " hat sich überkauft und verliert.").queue();
                player.incrementLooses();
                player.betAmount = 0;
                //  startRound(); TODO
            }
    }

    // TODO buttons
    private void makeDecision(Player player) {
        getBlackJackGameChannel().sendMessage("""
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
            getBlackJackGameChannel().sendMessage(currentPlayer.getName() + " erhält eine Karte.").queue();
            currentPlayer.hand.drawCard(playingDeck);
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
                playingDeck.drawCard()
        );
        currentPlayer.secondHand = new Hand(
                currentHand.getCard(1),
                playingDeck.drawCard()
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
    private void placeBet(Player player) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Auswahl der Einsätze");
        embedBuilder.addField(player.getName(), " ist an der Reihe.", false);
        embedBuilder.addField("Verfügbare Chips: " + player.chips,"Wähle deinen Einsatz.", false);
        ArrayList<ItemComponent> firstRowOfChips = new ArrayList<>();
        ArrayList<ItemComponent> secondRowOfChips= new ArrayList<>();
        for (int coinValue : new int[] { 1, 5, 10, 25, 50})
            firstRowOfChips.add(Button.primary("btnChip" + coinValue, String.valueOf(coinValue)));
        for (int coinValue : new int[] {100, 200, 500, 1000, 5000})
            secondRowOfChips.add(Button.danger("btnChip" + coinValue, String.valueOf(coinValue)));
        getBlackJackGameChannel().sendMessageEmbeds(embedBuilder.build())
                .addActionRow(firstRowOfChips)
                .addActionRow(secondRowOfChips).queue();
        int betAmount = 1;
        if (player.chips > betAmount)
            player.chips -= betAmount;
        player.betAmount = betAmount;
    }

    public void renderCards(Player player) {
        int value = player.hand.calculateValue();
        getBlackJackGameChannel().sendMessage("Spieler: " + player.getName()).queue();
        Suit suit;
        Rank rank;
        for (int index = 0; index < player.hand.getNumberOfCards(); index++) {
            suit = player.hand.getCard(index).suit();
            rank = player.hand.getCard(index).rank();
            File pngFile = FileReader.loadCard(suit, rank);
            getBlackJackGameChannel().sendMessage("Punkte: " + value)
                    .addFiles(FileUpload.fromData(pngFile)).queue();
        }
    }
}