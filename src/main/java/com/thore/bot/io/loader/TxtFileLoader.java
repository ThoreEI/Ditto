package com.thore.bot.io.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;

public class TxtFileLoader {
    public static String loadFile(String filePath) {
        String readLines = "";
        try {
           readLines = Files.lines(Paths.get(filePath)).collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        String[] list_jokes = readLines.split("\n");
        return list_jokes[new Random().nextInt(list_jokes.length)];
    }
}


