package com.thore.bot.games.blackJack.domain;

public class Player extends Person {
    public Hand secondHand;
    public int chips;
    public int betAmount;
    private int wins, looses, pushes;

    public Player(String name)  {
        super(name);
        this.chips = 0;
        this.betAmount = 0;
        this.wins = 0;
        this.looses = 0;
        this.pushes = 0;
        this.secondHand = null; // TODO  solution without null
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

    @Override
    public String toString() {
        return "Player{" +
                "wins=" + wins +
                ", looses=" + looses +
                ", pushes=" + pushes +
                '}';
    }
}
