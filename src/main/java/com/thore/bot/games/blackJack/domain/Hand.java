package com.thore.bot.games.blackJack.domain;
import java.util.ArrayList;

public class Hand {
    private final static ArrayList<Card> HAND = new ArrayList<>();

    public Hand(Card card1, Card card2 ) {
        HAND.add(card1);
        HAND.add(card2);
    }

    public int getValue() {
        int value = 0;
        for (Card card : HAND)
            value += card.getValue();
        while (value > 21 && HAND.toString().contains("Ace"))
            value -= 10;
        return value;
    }

    public boolean isBlackJack() {
        return getValue() == 21;
    }

    public ArrayList<Card> getHand() {
        return HAND;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : HAND)
            stringBuilder.append(card.toString()).append("\n");
        return stringBuilder.toString();
    }



}
