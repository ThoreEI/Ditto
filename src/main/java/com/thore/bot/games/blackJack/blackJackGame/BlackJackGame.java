package com.thore.bot.games.blackJack.blackJackGame;

import com.thore.bot.games.blackJack.domain.CardDeck;
import com.thore.bot.games.blackJack.domain.Hand;
import com.thore.bot.games.blackJack.domain.Player;
import com.thore.bot.games.blackJack.ui.UI;

public class BlackJackGame {
    public BlackJackGame( ) {
        int numberOfPlayers = UI.inputNumberOfPlayers();
        new UI(numberOfPlayers);
    }

    public static Player createPlayer(String username) {
        return new Player(username, createHand());
    }
    private static Hand createHand() {
        return new Hand(CardDeck.getCard(), CardDeck.getCard());
    }
}
