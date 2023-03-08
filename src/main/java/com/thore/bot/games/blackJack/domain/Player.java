package com.thore.bot.games.blackJack.domain;

public class Player extends Person {
    public Hand secondHand;
    public int money;
    private int wins, looses, pushes;

    public Player(String name)  {
        super(name);
        new Player(this.getName(), 0);
    }

    public Player(String name, int money)  {
        super(name);
        this.money = money;
        this.wins = 0;
        this.looses = 0;
        this.pushes = 0;
        this.secondHand = null; // TODO solution without null
    }

    public void incrementWins() {
        this.wins++;
    }
    public void incrementLooses() {
        this.looses++;
    }
    public void incrementPushes() {
        this.pushes++;
    }

    // a bust occurs when a player has more than 21 points
    public boolean isBust() {
        return hand.calculatePoints() > 21;
    }

    public boolean hasSecondHand() {
        return secondHand != null;
    }

    @Override
    public String toString() {
        return "Player{" +
                "wins=" + wins +
                ", looses=" + looses +
                ", pushes=" + pushes +
                ", money=" + money +
                '}';
    }


}
