package org.rgbmc.bungeebot.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.rgbmc.bungeebot.data.UserDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public abstract class SQLData {
    public static Dao<UserDTO, String> playerDTOs;
    private static SQLData instance;
    private final ConnectionSource connectionSource;

    public SQLData() throws SQLException {
        instance = this;
        connectionSource = getConnectionSource();
        Logger.setGlobalLogLevel(Level.WARNING);
        playerDTOs = DaoManager.createDao(connectionSource, UserDTO.class);
        if (!playerDTOs.isTableExists()) {
            TableUtils.createTable(this.connectionSource, UserDTO.class);
        }
    }

    public static SQLData getInstance() {
        return instance;
    }

    protected abstract ConnectionSource getConnectionSource() throws SQLException;

    public List<UserDTO> queryAll() throws SQLException {
        return playerDTOs.queryForAll();
    }

    public boolean createOrUpdate(UserDTO dto) throws SQLException {
        return playerDTOs.createOrUpdate(dto).isUpdated();
    }

    public UserDTO getData(String player) throws SQLException {
        return playerDTOs.queryForId(player);
    }

    public boolean hasPlayer(String player) throws SQLException {
        return playerDTOs.idExists(player);
    }

    public void close() {
        connectionSource.closeQuietly();
    }
}
