package com.thore.bot.games.blackJack.domain;
import java.util.ArrayList;

public class Hand {
    private final static ArrayList<Card> HAND = new ArrayList<>();

    public Hand(Card card1, Card card2 ) {
        HAND.add(card1);
        HAND.add(card2);
    }

    public int calculateValue() {
        int value = 0;
        int aceCount = 0;
        for (Card card : HAND) {
            value += card.getValue();
            if (card.getValue() == 11)
                aceCount++;
        }
        while (value > 21 && aceCount > 0) {
            aceCount--;
            value -= 10;
        }
        return value;
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
