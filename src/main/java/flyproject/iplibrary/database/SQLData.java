package flyproject.iplibrary.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import flyproject.iplibrary.Util;

import java.io.File;
import java.sql.SQLException;

public class SQLData {
    public static Dao<IPData, Long> ipDatas;
    public final ConnectionSource connectionSource;

    public SQLData(File database) throws SQLException {
        connectionSource = getConnectionSource(database);
        com.j256.ormlite.logger.Logger.setGlobalLogLevel(Level.WARNING);
        ipDatas = DaoManager.createDao(connectionSource, IPData.class);
        if (!ipDatas.isTableExists()) {
            TableUtils.createTable(this.connectionSource, IPData.class);
        }
    }

    protected ConnectionSource getConnectionSource(File path) throws SQLException {
        String url = "jdbc:sqlite:" + path.getPath();
        return new JdbcConnectionSource(url);
    }

    public boolean createOrUpdate(IPData dto) throws SQLException {
        return ipDatas.createOrUpdate(dto).isUpdated();
    }

    public IPData getData(long id) throws SQLException {
        return ipDatas.queryForId(id);
    }

    public IPData getData(String ip) throws SQLException {
        long digital_ip = Util.getIPLong(ip);
        Where<IPData, Long> where = ipDatas.queryBuilder().where();
        where.raw(digital_ip + " BETWEEN `start_digital` AND `end_digital`");
        return where.queryForFirst();
    }

    public void close() {
        connectionSource.closeQuietly();
    }
}
