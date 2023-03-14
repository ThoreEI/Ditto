package com.thore.bot.games.blackJack.domain;

public record Card(Suit suit, Rank rank) implements Comparable<Card> {

    @Override
    public int compareTo(Card card) {
        return this.getValue() - card.getValue(); // TODO testing
    }

    public int getValue() {
        return rank.getRankValue();
    }

    public String getCardDescription() {
        return rank.getRankName() + suit.getSuitName();
    }
}