package com.thore.bot.games.blackJack.blackJackGame;

import com.thore.bot.games.blackJack.domain.CardDeck;
import com.thore.bot.games.blackJack.domain.Hand;

public class BlackJackGame {
    public BlackJackGame(int numberOfPlayers) {
        CardDeck cardDeck = new CardDeck();

        for (int i = 0; i <25 ; i++) {
            Hand h1 = new Hand(cardDeck.getCard(), cardDeck.getCard());
            if (h1.toString().contains("Ace"))
                h1.getHand().add(cardDeck.getCard());
            System.out.println(h1.toString() + h1.getValue());
        }

    }
}
