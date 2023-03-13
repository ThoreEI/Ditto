package com.thore.bot.games.blackJack.domain;

public abstract class Person {
    private final String name;
    public Hand hand;

    public Person(String name) {
        this.name= name;
        this.hand=new Hand();
    }

    // player/dealer has a total card value of 21
    public boolean hasBlackJack() {
        return this.hand.calculateValue() == 21;
    }

    // a bust occurs when a player has more than 21 points
    public boolean isBust() {
        return hand.calculateValue() > 21;
    }

    public String getName() {
        return name;
    }
}