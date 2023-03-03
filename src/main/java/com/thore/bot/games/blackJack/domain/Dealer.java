package com.thore.bot.games.blackJack.domain;

public class Dealer extends Person {

    public Dealer(String username, Hand hand) {
        super(username, hand);
    }

    public void printHandOfCards(){
        System.out.println("The dealer has these cards");
        System.out.println(super.getHand().getCard(0));
        System.out.println("The second card is face down.");
    }
}