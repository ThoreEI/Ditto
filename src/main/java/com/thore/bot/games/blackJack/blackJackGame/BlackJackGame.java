package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.games.blackJack.domain.Dealer;
import com.thore.bot.games.blackJack.domain.Deck;
import com.thore.bot.games.blackJack.domain.Person;
import com.thore.bot.games.blackJack.domain.Player;
import com.thore.bot.games.blackJack.ui.UI;

import javax.swing.*;
import java.util.ArrayList;

public class BlackJackGame extends JPanel {
    private static ArrayList<Player> PLAYERS = new ArrayList<>();
    private Deck cardDeck, discardedDeck;
    private Person player, dealer;
    private int wins=0, looses=0, pushes=0;

    public final static int WIDTH_OF_CARD = 100;
    public final static int HEIGHT_OF_CARD = 155;
    public final static String PATH_IMAGES = "com/thore/bot/images/cards/";

    JButton btnHit, btnStand, btnNext;
    JLabel[] labelDealerCards, labelPlayerCards;
    JLabel labelScore, labelPlayerHandVal, labelDealerHandVal, labelGameMessage;

    public BlackJackGame() {
        setupGUI();
        buildDecks();
        createPlayers();
        startRound();
    }

    private void buildDecks() {
        cardDeck = new Deck();
        discardedDeck = new Deck();
        cardDeck.shuffle();
    }

    private void createPlayers(/*int numberOfPlayers*/) {
        //   for (int player=0; player<numberOfPlayers; player++) {
        Player player = createPlayer(UI.inputUsername());
        PLAYERS.add(player);
        dealer = new Dealer();
    }

    private void setupGUI() {
        JFrame frame = new JFrame("Ditto's BlackJack");
        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BlackJackGame blackJackGame = new BlackJackGame();
        frame.add(blackJackGame);
        frame.setVisible(true);
    }


    private void startRound() {
        if (wins > 0 || looses > 0 || pushes > 0) {
            System.out.println("Statistics: " + getStatistics());
            System.out.println(" Round starts!");
        }
        // TODO
    }

    private void revealCards() {
        for (Player player: PLAYERS)
            System.out.println(player.getName() + " # " + player.getHand().toString()+"\n");
    }

    public  Player createPlayer(String username) {
        return new Player(username);
    }

    public String getStatistics() {
        return "wins=" + wins + "\n" + "looses=" + looses + "\npushes=" + pushes;
    }

}
