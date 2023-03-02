package com.thore.bot.games.blackJack.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandTest {

    @Test
    public void getValueTest() {
        Hand hand = new Hand(new Card(Suit.CLUB, Rank.ACE), new Card(Suit.SPADE,Rank.ACE));
        hand.getHand().add(new Card(Suit.CLUB , Rank.TEN));
        assertTrue(12 == hand.calculateValue());
    }
}
