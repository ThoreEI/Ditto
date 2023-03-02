package com.thore.bot.games.blackJack.blackJackGame;

import com.thore.bot.games.blackJack.domain.CardDeck;
import com.thore.bot.games.blackJack.ui.UI;

public class BlackJackGame {
    public BlackJackGame( ) {
        CardDeck cardDeck = new CardDeck();
        new UI(cardDeck);
    }
}
