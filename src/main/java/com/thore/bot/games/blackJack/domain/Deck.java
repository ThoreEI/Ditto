package com.thore.bot.games.blackJack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private static ArrayList<Card> deck;

    public Deck() {
        createDeck();
    }

    private void createDeck() {
        deck = new ArrayList<>();
        for (Suit suit : Suit.values())
            for (Rank rank : Rank.values())
                deck.add(new Card(suit, rank));
        shuffle();
    }

    private static void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    private void emptyDeck() {
        getDeck().clear();
    }

    public void replenishWithCards(Deck discardedDeck) {
        this.addCards(discardedDeck.getDeck());
        shuffle();
        discardedDeck.emptyDeck();
    }

    public void addCards(ArrayList<Card> cards) {
        deck.addAll(cards);
    }

    public Card drawCard() {
        if (isEmpty())
            createDeck();
        // replenishWithCards(new Deck()); // TODO discarded deck
        return getDeck().remove(0);
    }

    public boolean isEmpty() {
        return deck.size()==0;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

}