package com.thore.bot.games.blackJack.domain;
import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public Card getCard(int index) {
        return hand.get(index);
    }

    public void drawCardFromDeck(Deck deck) {
        hand.add(deck.getCard());
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