package com.thore.bot.io.messenger;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class ImageMessenger {

    public static void sendPngToTextChannel(TextChannel textChannel, String pngFilePath) throws IOException {
        File pngFile = new File(pngFilePath);
        if (!pngFile.exists() || pngFile.isDirectory()) // TODO Debugging
            throw new FileNotFoundException("PNG file not found.");
        //InputStream pngStream = new FileInputStream(pngFile);
        byte[] pngData = Files.readAllBytes(pngFile.toPath());
        String pngBase64 = Base64.getEncoder().encodeToString(pngData);
        System.out.println(pngBase64);
        textChannel.sendMessage(pngBase64).queue(); // TODO
    }
}
