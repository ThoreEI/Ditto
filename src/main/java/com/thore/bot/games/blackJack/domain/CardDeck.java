package com.thore.bot.games.blackJack.domain;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    private final static ArrayList<Card> CARD_DECK = new ArrayList<>();

    public CardDeck() {
        for (String color : new String[]{"Clubs","Spades","Hearts","Diamonds"}) {
            for (int number=2; number<=10; number++)
                CARD_DECK.add(new Card(color, number+""));
            for (String card : new String[] {"Jack", "Queen", "King", "Ace"})
                CARD_DECK.add(new Card(color, card));
        }
        Collections.shuffle(CARD_DECK);
    }

    public static Card getCard() {
        if (CARD_DECK.isEmpty())
            throw new RuntimeException();
        return CARD_DECK.remove(0);
    }
}
