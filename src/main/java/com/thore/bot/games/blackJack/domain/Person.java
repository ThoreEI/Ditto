package com.thore.bot.games.blackJack.domain;

public abstract class Person {
    private final String name;
    public Hand hand;

    public Person(String name) {  // TODO name von discord
        this.name= name;
        this.hand=new Hand();
    }

    public void hit(Deck cardDeck, Deck discardedDeck) {
        if (cardDeck.isEmpty())
            cardDeck.replenishWithCards(discardedDeck);
        this.hand.drawCard(cardDeck);
    }


    public boolean hasBlackJack() {
        return this.hand.calculatePoints() == 21;
    }

    public String getName() {
        return name;
    }
    
}