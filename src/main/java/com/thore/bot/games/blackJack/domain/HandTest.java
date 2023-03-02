package com.thore.bot.games.blackJack.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandTest {

    @Test
    public void getValueTest() {
        Hand hand = new Hand(new Card("Clubs", "Ace"), new Card("Spaded", "0"));
        hand.getHand().add(new Card("Spades" , "Ace"));
        assertTrue(12 == hand.getValue());
    }
}
