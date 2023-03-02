package com.thore.bot.games.blackJack.domain;

import java.util.ArrayList;

public class CardDeck {
    private static ArrayList<Card> cardDeck = new ArrayList<>();

    public CardDeck() {
        for (Suit suit : Suit.values())
            for (Rank rank : Rank.values())
                cardDeck.add(new Card(suit, rank));
        shuffle();
    }

    //  Collections.shuffle(cardDeck, new Random());
    public void shuffle() {
        ArrayList<Card> shuffledDeck = new ArrayList<>();
        while (cardDeck.size() > 0) {
            int indexCardToPull = (int) (Math.random()*cardDeck.size()-1);
            shuffledDeck.add(cardDeck.remove(indexCardToPull));
        }
       cardDeck = shuffledDeck;
    }

    public static Card getCard() {
        return cardDeck.remove(0);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Card card: cardDeck)
            output.append(card).append("\n");
        return output.toString();
    }
}
