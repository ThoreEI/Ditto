package com.thore.bot.games.blackJack.domain;

public class Player {


    private String username;
    private Hand hand;
    private int points = 0;



    public Player(String username, Hand hand) {
        this.username=username;
        this.hand = hand;
    }

    public String getUsername() {
        return username;
    }

    public Hand getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }
}
