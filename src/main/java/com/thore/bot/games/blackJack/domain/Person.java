package com.thore.bot.games.blackJack.domain;

public abstract class Person {
    private String username;
    private Hand hand;

    public Person(String username, Hand hand) {
        this.username=username;
        this.hand=hand;
    }

    public boolean hasBlackJack() {
        return this.getHand().calculateValue() == 21;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + username + '\'' + "; hand=" + hand + '}';
    }

    public String getUsername() {
        return username;
    }

    public Hand getHand() {
        return hand;
    }
}
