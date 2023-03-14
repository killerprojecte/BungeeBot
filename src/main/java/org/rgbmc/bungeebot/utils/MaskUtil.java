package org.rgbmc.bungeebot.utils;

import org.rgbmc.bungeebot.BungeeBot;

import java.util.Random;

public class MaskUtil {
    public static String getMaskQQ(String origin){
        StringBuilder builder = new StringBuilder(origin);
        StringBuilder mask = new StringBuilder();
        for (int i = 3; i<builder.length() - 3;i++){
            mask.append("*");
        }
        builder.replace(BungeeBot.Companion.getInstance().getConfig().getInt("mask.start"), builder.length() - BungeeBot.Companion.getInstance().getConfig().getInt("mask.end"), mask.toString());
        return builder.toString();
    }

    public static String getRandomString(int length) {
        String str = BungeeBot.Companion.getInstance().getConfig().getString("random-key");
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
