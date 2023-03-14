package org.rgbmc.bungeebot.utils;

import java.util.UUID;

public class IPUpdateData {
    public IPUpdateData(String newIP, UUID uuid, String player){
        this.ip = newIP;
        this.uuid = uuid;
        this.player = player;
    }

    public String ip;

    public UUID uuid;

    public String player;
}
