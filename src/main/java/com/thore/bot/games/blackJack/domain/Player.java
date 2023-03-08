package com.thore.bot.games.blackJack.domain;

public class Player extends Person {
    public Hand secondHand;
    private int wins=0;
    private int looses=0;
    private int pushes=0;
    public int money;

    @Override
    public String toString() {
        return "Player{" +
                "wins=" + wins +
                ", looses=" + looses +
                ", pushes=" + pushes +
                ", money=" + money +
                '}';
    }

    public Player(String name)  {
        super(name);
        new Player(this.getName(), 0);
    }

    public Player(String name, int money)  {
        super(name);
        this.secondHand = null; // TODO solution without null
        this.money = money;
    }

    public boolean hasSecondHand() {
        return secondHand != null;
    }
}
