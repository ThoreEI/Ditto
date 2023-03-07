package com.thore.bot.io.reader;

import com.thore.bot.io.messenger.ImageMessenger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;

public class FileReader {
    private final static String JOKES_TXT = "src/main/java/com/thore/bot/io/files/txtFiles/jokes.txt";
    private final static String CARDS_PNG = "src/main/java/com/thore/bot/io/files/pngFiles/";

    public static String loadJoke() {
        String readLines = "";
        try {
            readLines = Files.lines(Paths.get(JOKES_TXT)).collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        String[] jokes = readLines.split("\n");
        return jokes[new Random().nextInt(jokes.length)];
    }

    public static Image loadCard(String filename) throws IOException {
        return ImageIO.read(new File(CARDS_PNG+filename));

    }
}


