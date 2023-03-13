package com.thore.bot.games.blackJack.domain;

public record Card(Suit suit, Rank rank) implements Comparable<Card> {

    @Override
    public int compareTo(Card card) {
        return this.getValue() - card.getValue(); // TODO testen
    }

    public int getValue() {
        return rank.getRankValue();
    }
}