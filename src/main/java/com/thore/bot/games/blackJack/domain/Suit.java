package com.thore.bot.games.blackJack.domain;

public enum Suit {
    CLUB("Clubs"),
    DIAMOND("Diamonds"),
    HEART("Hearts"),
    SPADE("Spades");


    private final static Suit[] SUITS = new Suit []{Suit.CLUB,Suit.SPADE,Suit.HEART,Suit.DIAMOND};
    private final String suitName;

    Suit (String suitName) {
        this.suitName = suitName;
    }

    public String getSuitName() {
        return suitName;
    }

    @Override
    public String toString() {
        return suitName;
    }
}
