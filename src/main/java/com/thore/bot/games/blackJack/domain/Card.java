package com.thore.bot.games.blackJack.domain;

public class Card implements Comparable<Card> {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public int compareTo(Card card) {
        return this.getValue() - card.getValue(); // TODO testen
    }

    public int getValue() {
        return rank.getRankValue();
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }
}