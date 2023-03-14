package flyproject.iplibrary;

import flyproject.iplibrary.database.IPData;
import flyproject.iplibrary.database.SQLData;

import java.io.File;
import java.sql.SQLException;

public class IPLibrary {
    public SQLData sqlData = null;
    public IPLibrary(File database){
        try {
            sqlData = new SQLData(database);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IPData query(String ip){
        IPData data = null;
        try {
            data = sqlData.getData(ip);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
