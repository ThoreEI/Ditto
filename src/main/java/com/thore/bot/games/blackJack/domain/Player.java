package com.thore.bot.games.blackJack.domain;

public class Player extends Person {
    private int wins=0;
    private int looses=0;
    private int pushes=0;
    private int money;

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
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
}
