package org.rgbmc.bungeebot.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "bind_users")
public class UserDTO implements Serializable {
    @DatabaseField(id = true)
    private String player;
    @DatabaseField
    private long qq;
    @DatabaseField
    private String ip;

    public UserDTO(String player, long qq, String ip) {
        this.player = player;
        this.qq = qq;
        this.ip = ip;
    }

    public UserDTO() {
    }

    public String getPlayer() {
        return player;
    }

    public long getQQ() {
        return qq;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
