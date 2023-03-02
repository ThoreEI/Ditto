package com.thore.bot.games.blackJack.domain;

import java.util.ArrayList;
import java.util.Collections;


public class CardDeck {
    private final static ArrayList<Card> CARD_DECK = new ArrayList<>();
    private final static String[] COLORS = {"Clubs", "Spades", "Hearts", "Diamonds"};
    private final static String[] HONOR_CARDS  =  {"Jack", "Queen", "King", "Ace"};

    public CardDeck() {
        for (String color : COLORS) {
            for (int number = 2; number<=10; number++)
                CARD_DECK.add(new Card(color, number+""));
            for (String card : HONOR_CARDS)
                CARD_DECK.add(new Card(color, card));
        }
        Collections.shuffle(CARD_DECK);
    }

    public ArrayList<Card> getCardDeck() {
        return CARD_DECK;
    }

    public Card getCard() {
        if (CARD_DECK.isEmpty())
            throw new RuntimeException();
        return CARD_DECK.remove(0);
    }

}
