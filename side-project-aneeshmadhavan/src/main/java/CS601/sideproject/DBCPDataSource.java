package CS601.sideproject;

import com.google.gson.Gson;
import org.apache.commons.dbcp.BasicDataSource;
import utilities.DbConfig;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Example of using Apache DBCP ConnectionPool.
 * Taken from https://www.baeldung.com/java-connection-pooling.
 * Taken from code examples.
 */
public class DBCPDataSource {

    // Apache commons connection pool implementation
    private static BasicDataSource ds = new BasicDataSource();
    private static final String DB_CONFIG_FILENAME = "dbconfig.json";


    // This code inside the static block is executed only once: the first time the class is loaded into memory.
    // -- https://www.geeksforgeeks.org/static-blocks-in-java/
    static {
        Gson gson = new Gson();
        DbConfig dbConfig = null;
        try {
            dbConfig = gson.fromJson(new FileReader(DB_CONFIG_FILENAME), DbConfig.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // if the config file cannot be found
        if(dbConfig == null) {
            System.exit(1);
        }
        ds.setUrl("jdbc:mysql://localhost:3306/" + dbConfig.getDatabase());
        ds.setUsername(dbConfig.getUsername());
        ds.setPassword(dbConfig.getPassword());
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
    }

    /**
     * Return a Connection from the pool.
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource(){ }
}