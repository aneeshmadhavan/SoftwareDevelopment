package cs601.utilities;

/**
 * Class that contains the database info object.
 * Taken from code examples and modified.
 */
public class DbConfig {

    private final String database;
    private final String username;
    private final String password;

    /**
     * Config class constructor.
     *
     * @param database
     * @param username
     * @param password
     */
    public DbConfig(String database, String username, String password) {
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Return the database property.
     *
     * @return
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Return the username property.
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return the password property.
     *
     * @return
     */
    public String getPassword() {
        return password;
    }
}