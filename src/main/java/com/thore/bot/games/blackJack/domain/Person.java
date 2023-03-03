package com.thore.bot.games.blackJack.domain;

public abstract class Person {
    private final String name;
    private Hand hand;
    private int wins, looses, pushes;

    public Person(String username, Hand hand) {
        this.name =username;
        this.hand=hand;
    }

    public void hit(Deck cardDeck, Deck discardDeck) {
        if (cardDeck.isEmpty())
            cardDeck.refillCardsFromDiscardDeck(discardDeck);
        this.hand.drawCardFromDeck(cardDeck);
    }


    public boolean hasBlackJack() {
        return this.getHand().calculateValue() == 21;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + "; hand=" + hand + '}';
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public int getWins() {
        return wins;
    }

    public int getLooses() {
        return looses;
    }

    public int getPushes() {
        return pushes;
    }
}
