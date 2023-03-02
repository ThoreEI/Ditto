package com.thore.bot.games.blackJack.domain;

public class Card {
    private final String color;
    private final String card;

    public Card(String color, String card) {
        this.color = color;
        this.card = card;
    }

    public int getValue() {
        return switch (card) {
            case "Jack", "Queen", "King" -> 10;
            case "Ace" -> 11;
            default -> Integer.parseInt(card);
        };
    }

    public String getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "Card = ["+card+"], Color = ["+color+"]";
    }
}
