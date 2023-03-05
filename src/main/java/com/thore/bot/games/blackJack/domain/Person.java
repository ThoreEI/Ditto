package com.thore.bot.games.blackJack.domain;

import com.thore.bot.games.blackJack.blackJackGame.BlackJackGame;

import javax.swing.*;
import java.awt.*;

public abstract class Person {
    private String name;
    private Hand hand;

    public Person(String name) {
        this.name= name;  // TODO check
    }

    public void hit(Deck cardDeck, Deck discardDeck) {
        if (cardDeck.isEmpty())
            cardDeck.refillCardsFromDiscardDeck(discardDeck);
        this.hand.drawCardFromDeck(cardDeck);
    }

    public void printHand(JLabel[] imagesOfCards) {
        System.out.println(this.name + "'s hand:");
        System.out.println(this.hand + " Value: " + this.hand.calculateValue());

        for(int i = 0; i < 11; i++)
            imagesOfCards[i].setVisible(false);

        for(int number=0; number < hand.getNumberOfRemainingCards(); number++) {
            String rank = this.hand.getCard(number).getRank().toString();
            String suit = this.hand.getCard(number).getSuit().toString();
            String filename = "_" + rank + "_" +  suit + ".png";

            imagesOfCards[number].setIcon(
                    new ImageIcon(
                            new ImageIcon(BlackJackGame.PATH_IMAGES+filename)
                                    .getImage()
                                    .getScaledInstance(
                                            BlackJackGame.WIDTH_OF_CARD,
                                            BlackJackGame.HEIGHT_OF_CARD,
                                            Image.SCALE_DEFAULT
                                    )
                    )
            );
            imagesOfCards[number].setVisible(true);
        }

    }

    public boolean hasBlackJack() {
        return this.getHand().calculateValue() == 21;
    }

    public String getName() {
        return name;
    }

    public Hand getHand(){
        return this.hand;
    }

}
