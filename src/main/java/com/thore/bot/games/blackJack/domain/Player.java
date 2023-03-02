package com.thore.bot.games.blackJack.domain;

public class Player extends Person {
    private int money = 0;


    public Player(String username, Hand hand) {
        super(username, hand);
    }
    public Player(String username, Hand hand, int money) { // TODO Ask using money
        super(username, hand);
        this.money += money;
    }


    public int getMoney() {
        return money;
    }
}
