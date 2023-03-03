package com.thore.bot.games.blackJack.ui;

import com.thore.bot.games.blackJack.domain.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static ArrayList<Player> PLAYERS = new ArrayList<>();

    public UI(/*int numberOfPlayers*/) {
        revealCards();
    }

    private void revealCards() {
        for (Player player : PLAYERS)
            System.out.println(player.getName() + " # " + player.getHand().toString() + "\n");
    }

    // TODO regex
    public static String inputUsername() {
        System.out.println("Enter a username.\n > ");
        String username = SCANNER.nextLine();
        return username.length() > 0 ? username : "Nameless";
    }

    public static int inputNumberOfPlayers() {
        System.out.println("Number of players.");
        System.out.println(" > ");
        int input = Integer.parseInt(SCANNER.nextLine());
        return 1 <= input && input <= 4 ? input : 4;
    }

    public static boolean makeDecision () {
        System.out.println("1 -> Hit");
        System.out.println("2 -> Stand");
        System.out.print(" > ");
        try {
            int input = Integer.parseInt(SCANNER.nextLine());
            if (input == 1)
                return true;
            else if (input == 2) // TODO  elegant lösen
                return false;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return makeDecision();
    }
}
