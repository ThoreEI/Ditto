package com.thore.bot.games.blackJack.domain;


import com.thore.bot.games.blackJack.ui.UI;

public class Player extends Person {
    private int money;

    public Player(String username, Hand hand) {
        super(username, hand);
        new Player(username, hand, 100);
    }

    public Player(String username, Hand hand, int money) {
        super(username, hand); //  TODO Ask using money
        this.money += money;
    }

    public void makeDecision(Deck deck, Deck discard) {
        boolean hasDecidedToHit = UI.makeDecision();
        if (hasDecidedToHit)
            this.hit(deck, discard);
        if (this.getHand().calculateValue() < 21)
                     this.makeDecision(deck, discard);
    }

    public int getMoney() {
        return money;
    }
}
