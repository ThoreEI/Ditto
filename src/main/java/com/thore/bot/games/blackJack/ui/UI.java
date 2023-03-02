package com.thore.bot.games.blackJack.ui;

import com.thore.bot.games.blackJack.domain.CardDeck;
import com.thore.bot.games.blackJack.domain.Hand;

import java.util.Scanner;

public class UI {
    private final static Scanner SCANNER = new Scanner(System.in);

    public UI(CardDeck cardDeck) {

        initializePlayers();
    }

    private void initializePlayers() {
        System.out.println(" Player");
        System.out.print(" > ");
        String player1 = SCANNER.nextLine();
        Hand hand1 = new Hand(CardDeck.getCard(), CardDeck.getCard());
        String player2 = SCANNER.nextLine();
        Hand hand2 = new Hand(CardDeck.getCard(), CardDeck.getCard());
    }
}
