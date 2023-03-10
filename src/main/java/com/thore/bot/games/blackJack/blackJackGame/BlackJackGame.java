package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.Bot;
import com.thore.bot.games.blackJack.domain.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import java.time.Instant;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BlackJackGame {
    @NotNull
    public final static TextChannel BLACK_JACK_GAME_CHANNEL = Objects.requireNonNull(Bot.getJda().getTextChannelById(Bot.getConfig().get("BLACK_JACK_GAME_ID")));
    private static Deck playingDeck;
    private static ArrayList<Player> players;
    private static Dealer dealer;
    private int wins, looses, pushes;

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
        while (true) {
            printSpace();
            if (!(wins > 0 || looses > 0  || pushes > 0))
                BLACK_JACK_GAME_CHANNEL.sendMessage("Willkommen zum Black Jack!").queue();
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
                BLACK_JACK_GAME_CHANNEL.sendMessage("Unentschieden. " + player.getName() + " erhält seinen Einsatz zurück.").queue();
                pushes++;
                player.chips += player.betAmount;
            } else if (player.hasBlackJack()) {
                BLACK_JACK_GAME_CHANNEL.sendMessage(player.getName() + " hat ein BlackJack! Gewinn: " + player.betAmount * 1.5).queue();
                wins++;
                player.chips += player.betAmount * 1.5;
            } else if (dealer.hasBlackJack()) {
                BLACK_JACK_GAME_CHANNEL.sendMessage(dealer.getName() + " hat ein BlackJack! " + player.getName() + " hat verloren.").queue();
                looses++;
            }
            player.betAmount = 0;
            startRound();
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
        BLACK_JACK_GAME_CHANNEL.sendMessage("""
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
            BLACK_JACK_GAME_CHANNEL.sendMessage(currentPlayer.getName() + " erhält eine Karte.").queue();
            printSpace();
            currentPlayer.hand.drawCard(playingDeck);
            renderCards();
            checkForBlackJacks();
            checkForBusts();
            BLACK_JACK_GAME_CHANNEL.sendMessage("1 -> Eine weitere Karte?").queue();
            BLACK_JACK_GAME_CHANNEL.sendMessage("2 -> Keine weitere Karte?").queue();
            int selection =1; // TODO
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
        BLACK_JACK_GAME_CHANNEL.sendMessage(player.getName() + " gibt auf. Die Hälfte des Einsatzes geht zurück.").queue();
        printSpace();
        looses++;
        player.chips += player.betAmount/2;
        player.betAmount=0;
    }

    // TODO dc-channel
    private void placeBet(Player currentPlayer) {
        BLACK_JACK_GAME_CHANNEL.sendMessage(currentPlayer.getName() + " hat "  + currentPlayer.chips + " Chips.").queue();
        BLACK_JACK_GAME_CHANNEL.sendMessage("Wie hoch ist dein Einsatz?").queue();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Example Embed");
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.addField("Field 1", "This is the first field", false);
        embedBuilder.addField("Field 2", "This is the second field", false);

        ArrayList<ItemComponent> buttonList = new ArrayList<>();
        buttonList.add(Button.primary("button1", "Button 1"));
        buttonList.add(Button.success("button2", "Button 2"));
        buttonList.add(Button.danger("button3", "Button 3"));
        BLACK_JACK_GAME_CHANNEL.sendMessageEmbeds(embedBuilder.build()).addActionRow(buttonList).queue();

        int betAmount = 1;
//        if (betAmount <= 0 || currentPlayer.chips < betAmount) {
//            System.err.println("Du musst mindestens einen Chip und maximal " + currentPlayer.chips + " Chips setzen.");
//            placeBet(currentPlayer);
//        }
        currentPlayer.chips -=betAmount;
        currentPlayer.betAmount = betAmount;
        printSpace();
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
            printSpace();
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
            printSpace();
        }
    }

    private static void printSpace() {
        for (int printedLines=0; printedLines<2; printedLines++)
            BLACK_JACK_GAME_CHANNEL.sendMessage("|").queue();
    }

    @Override
    public String toString() {
        return "Punktestand: {" + "wins=" + wins + ", looses=" + looses + ", pushes=" + pushes + '}';
    }

}