package com.thore.bot.games.blackJack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        for (Suit suit : Suit.values())
            for (Rank rank : Rank.values())
                deck.add(new Card(suit, rank));
        shuffle();
    }

    public Deck(Deck toCopy) {
        deck = new ArrayList<>(); // TODO
        Collections.copy(deck, toCopy.getDeck());
    }

    public void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    public void addCards(ArrayList<Card> cards) {
        deck.addAll(cards);
    }

    public void refillCardsFromDiscardDeck(Deck discardDeck) {
        this.addCards(discardDeck.getDeck());
        this.shuffle();
        discardDeck.emptyDeck();
    }

    private void emptyDeck() {
        this.getDeck().clear();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Card getCard() {
        return deck.remove(0);
    }

    public boolean isEmpty() {
        return deck.size()==0;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Card card: deck)
            output.append(card).append("\n");
        return output.toString();
    }
}