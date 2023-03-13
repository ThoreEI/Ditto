package com.thore.bot.games.blackJack.domain;
import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> hand;


    public Hand() {
        hand = new ArrayList<>();
    }

    public Hand(Card card1, Card card2) {
        hand = new ArrayList<>();
        hand.add(card1);
        hand.add(card2);
    }

    public Card getCard(int index) {
        return hand.get(index);
    }

    public void drawCard(Deck deck) {
        hand.add(deck.drawCard());
    }


    public void shuffleHandIntoDeck(Deck deck) {
        deck.addCards(hand);
        hand.clear();
    }

    public int calculateValue() {
        int value = 0;
        int numberOfAces = 0;
        for (Card card : this.hand) {
            value += card.getValue();
            if (card.getValue() == 11)
                numberOfAces++;
        }
        while (numberOfAces > 0 && value > 21) {
            numberOfAces--;
            value -= 10;
        }
        return value;
    }

    public boolean isPair() {
        return hand.size() == 2 && getCard(0).getValue() == getCard(1).getValue();
    }

    public int getNumberOfCards() {
        return hand.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : hand)
            stringBuilder.append(card.toString()).append("\n");
        return stringBuilder.toString();
    }
}