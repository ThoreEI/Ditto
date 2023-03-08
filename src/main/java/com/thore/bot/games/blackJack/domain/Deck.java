package com.thore.bot.games.blackJack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private static ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        for (Suit suit : Suit.values())
            for (Rank rank : Rank.values())
                deck.add(new Card(suit, rank));
        shuffle();
    }

    public Deck(Deck other) {
        Collections.copy(this.getDeck(), other.getDeck());  // TODO   benötigt?
    }

    public void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    private void emptyDeck() {
        getDeck().clear();
    }

    public void replenishWithCards(Deck discardedDeck) {
        this.addCards(discardedDeck.getDeck());
        this.shuffle();
        discardedDeck.emptyDeck();
    }

    public void addCards(ArrayList<Card> cards) {
        deck.addAll(cards);
    }

    public Card drawCard() {
        if (isEmpty()) // TODO verhindern
            throw new IndexOutOfBoundsException("Der Kartenstapel ist leer");
        return getDeck().remove(0);
    }

    public boolean isEmpty() {
        return deck.size()==0;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Card card: deck)
            output.append(card).append("\n");
        return output.toString();
    }
}