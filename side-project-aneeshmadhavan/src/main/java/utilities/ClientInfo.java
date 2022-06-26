package utilities;

/**
 * A class to maintain info about each client.
 * Taken from code examples.
 */
public class ClientInfo {

    private String name;
    private String email;



    /**
     * Constructor
     *
     * @param name
     */
    public ClientInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * return name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * return email.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }
}