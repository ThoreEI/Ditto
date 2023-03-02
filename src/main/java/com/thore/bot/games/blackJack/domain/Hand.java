package com.thore.bot.games.blackJack.domain;
import java.util.ArrayList;

public class Hand {


    private final ArrayList<Card> hand;
    public Hand(Card card1, Card card2 ) {
        hand = new ArrayList<>();
        hand.add(card1);
        hand.add(card2);
    }



    public int getValue() {
        int value = 0;
        for (Card card : hand)
            value += card.getValue();
        while (value > 21 && hand.toString().contains("Ace"))
            value -= 10;
        return value;
    }

    public boolean isBlackJack() {
        return getValue() == 21;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : hand)
            stringBuilder.append(card.toString()).append("\n");
        return stringBuilder.toString();
    }



}
