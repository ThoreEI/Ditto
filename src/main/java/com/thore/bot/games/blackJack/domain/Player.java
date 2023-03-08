package com.thore.bot.games.blackJack.domain;

public class Player extends Person {
    public Hand secondHand;
    public int chips;
    public int betAmount;
//    private int wins, looses, pushes;

    public Player(String name)  {
        super(name);
        new Player(this.getName(), 100);
    }

    public Player(String name, int chips)  {
        super(name);
        this.chips = chips;
        this.betAmount = 0;
        //  this.wins = 0;
        //  this.looses = 0;
        // this.pushes = 0;
        this.secondHand = null; // TODO solution without null
    }

    /*
    public void incrementWins() {
        this.wins++;
    }
    public void incrementLooses() {
        this.looses++;
    }
    public void incrementPushes() {
        this.pushes++;
    }

    public boolean hasSecondHand() {
        return secondHand != null;
    }
     */
}
