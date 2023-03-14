package com.thore.bot.games.blackJack.domain;
import java.util.ArrayList;

public class Hand {


    private final ArrayList<Card> cards;
    public Hand() {
        cards = new ArrayList<>();
    }

    public Hand(Card card1, Card card2) {
        cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public void drawCard(Deck deck) {
        cards.add(deck.drawCard());
    }


    public int calculateValue() {
        int value = 0;
        int numberOfAces = 0;
        for (Card card : this.cards) {
            value += card.getValue();
            if (card.rank().getRankName().equals("Ace"))
                numberOfAces++;
        }
        while (numberOfAces > 0 && value > 21) {
            numberOfAces--;
            value -= 10;
        }
        return value;
    }

    public boolean isPair() {
        return cards.size() == 2 && getCard(0).getValue() == getCard(1).getValue();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : cards)
            stringBuilder.append(card.toString()).append("\n");
        return stringBuilder.toString();
    }
}