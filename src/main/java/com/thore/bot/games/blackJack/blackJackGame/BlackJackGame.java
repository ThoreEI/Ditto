package com.thore.bot.games.blackJack.blackJackGame;
import com.thore.bot.games.blackJack.domain.*;
import com.thore.bot.games.blackJack.ui.*;
import java.util.ArrayList;

public class BlackJackGame {
    private final static ArrayList<Player> PLAYERS = new ArrayList<>();
    private Deck cardDeck, discardedDeck;
    private Player player;
    private Dealer dealer;
    private int wins=0, looses=0, pushes=0;

    public BlackJackGame() {
        cardDeck = new Deck();
        initializePlayers();
        startRound();
    }

    private void initializePlayers(/*int numberOfPlayers*/) {
            Player player = createPlayer(UI.inputUsername());
            PLAYERS.add(player);
        }

    private void startRound() {
        if (wins > 0 || looses > 0 || pushes > 0)
            System.out.println(getStatistics());
        // TODO
    }

    private void revealCards() {
        for (Player player: PLAYERS)
            System.out.println(player.getName() + " # " + player.getHand().toString()+"\n");
    }

    public  Player createPlayer(String username) {
        return new Player(username, createHand());
    }

    private Hand createHand() {
        return new Hand(cardDeck.getCard(), cardDeck.getCard());
    }

    public String getStatistics() {
        return "wins=" + wins + "\n" + "looses=" + looses + "\npushes=" + pushes;
    }

}
