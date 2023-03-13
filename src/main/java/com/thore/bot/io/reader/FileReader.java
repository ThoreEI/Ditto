package com.thore.bot.io.reader;

import com.thore.bot.games.blackJack.domain.Rank;
import com.thore.bot.games.blackJack.domain.Suit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;


public class FileReader  {
    private final static String PATH_JOKES_TXT = "src/main/java/com/thore/bot/io/files/txtFiles/jokes.txt";
    private final static String PATH_CARDS_PNG = "src/main/java/com/thore/bot/io/files/pngFiles/";


    public static String loadJoke() {
        String readLines = "";

        try {
            readLines = Files.lines(Paths.get(PATH_JOKES_TXT)).collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            System.err.println("An error occurred while loading a joke.");
        }

        String[] jokes = readLines.split("\n");
        return jokes[new Random().nextInt(jokes.length)];
    }

    public static File loadCard(Suit suit, Rank rank) {
        String filename = rank.getRankName() + suit.getSuitName() + ".png";
        return new File(PATH_CARDS_PNG + filename);
    }
}


