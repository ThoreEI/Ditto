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

    private static void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    public Card drawCard() {
        return getDeck().remove(0);
    }

    private void emptyDeck() {
        getDeck().clear();
    }

    public void addCards(ArrayList<Card> cards) {
        deck.addAll(cards);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void replenishWithCards(Deck discardedDeck) {
        this.addCards(discardedDeck.getDeck());
        shuffle();
        discardedDeck.emptyDeck();
    }
}