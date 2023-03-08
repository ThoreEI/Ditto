package com.thore.bot.io.messenger;
import com.thore.bot.Bot;
import com.thore.bot.io.reader.FileReader;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

public class ImageMessenger  {
    public final static  String BLACK_JACK_SPIEL_CHANNEL = Bot.getConfig().get("BOT_CHANNEL");


    public static void sendPngToTextChannel(String pngFilePath) throws IOException {
        File pngFile = new File(pngFilePath);
        if (!pngFile.exists()) // TODO Debugging
            throw new FileNotFoundException("PNG file not found.");
        //InputStream pngStream = new FileInputStream(pngFile);
        byte[] pngData = Files.readAllBytes(pngFile.toPath());
        System.out.println(Arrays.toString(pngData));
        String pngBase64 = Base64.getEncoder().encodeToString(pngData);
        try {
            BufferedImage newCard = FileReader.loadCard(pngFilePath);


        } catch (IOException e) {
            System.err.println("An error occurred while loading a card.");
        }
    }
}
