package com.thore.bot.games.blackJack.domain;

public class Card implements Comparable<Card> {

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Card (Card toCopy) {
        this.suit = toCopy.getSuit();
        this.rank = toCopy.getRank();
    }

    @Override
    public int compareTo(Card card) {
        return this.getValue() - card.getValue(); // TODO testen
    }

    public int getValue() {
        return rank.rankValue;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return ("[" + rank + " of " + suit + "] (" + this.getValue() + ")");
    }
}