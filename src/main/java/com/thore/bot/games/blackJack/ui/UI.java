package com.thore.bot.games.blackJack.ui;

import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;
import com.thore.bot.games.blackJack.domain.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static ArrayList<Player> PLAYERS = new ArrayList<>();

    public UI(int numberOfPlayers) {
        initializePlayers(numberOfPlayers);
        revealCards();
    }

    private void revealCards() {
        for (Player player: PLAYERS)
            System.out.println(player.getUsername() + " # " + player.getHand().toString()+"\n");
    }

    private void initializePlayers(int numberOfPlayers) { // TODO regex
        for (int number = 0; number < numberOfPlayers; number++) {
            System.out.println("Username Player " + (PLAYERS.size()+1));
            System.out.print(" > ");
            String username = SCANNER.nextLine();
            Player player = BlackJackGame.createPlayer(username);
            PLAYERS.add(player);
        }
    }

    public static int inputNumberOfPlayers() {
        System.out.println("Number of players.");
        System.out.println(" > ");
        int input = Integer.parseInt(SCANNER.nextLine());
        return 1<=input && input<=4 ? input:4;
    }
}
