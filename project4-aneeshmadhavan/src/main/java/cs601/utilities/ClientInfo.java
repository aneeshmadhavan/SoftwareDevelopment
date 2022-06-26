package cs601.utilities;

/**
 * A class to maintain info about each client.
 * Taken from code examples.
 */
public class ClientInfo {

    private final String name;
    private final String email;

    /**
     * Constructor.
     *
     * @param name
     * @param email
     */
    public ClientInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Getter for name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for email.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }
}