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
        while (aceCount > 0 && value > 21) {
            aceCount--;
            value -= 10;
        }
        return value;
    }

    public void shuffleHandCardsIntoDeck(Deck receivingDeck) {
        receivingDeck.addCards(HAND);
    }

    public Card getCard(int index) {
        return HAND.get(index);
    }

    public void drawCardFromDeck(Deck deck) {
        HAND.add(deck.getCard());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : HAND)
            stringBuilder.append(card.toString()).append("\n");
        return stringBuilder.toString();
    }
}
