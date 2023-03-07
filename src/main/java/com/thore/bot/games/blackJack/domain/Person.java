package com.thore.bot.games.blackJack.domain;

public abstract class Person {
    private final String name;
    private Hand hand;

    public Person(String name) {  // TODO name von discord
        this.name= name;
        this.hand=new Hand();
    }

    public void hit(Deck cardDeck, Deck discardedDeck) {
        if (cardDeck.isEmpty())
            cardDeck.replenishWithCards(discardedDeck);
        this.hand.drawCardFromDeck(cardDeck);
    }


    public boolean hasBlackJack() {
        return this.getHand().calculateValue() == 21;
    }

    public String getName() {
        return name;
    }

    public Hand getHand(){
        return this.hand;
    }
}