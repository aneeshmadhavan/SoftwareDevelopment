package cs601.utilities;

import java.util.Date;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the Transactions object.
 *
 * @author Aneesh Madhavan
 */
public class Transactions {
    private final String name;
    private final String location;
    private final Date dateTime;
    private final int tickets;
    private final int id;

    /**
     * Constructor for class.
     *
     * @param name
     * @param location
     * @param dateTime
     * @param tickets
     * @param id
     */
    public Transactions(String name, String location, Date dateTime, int tickets, int id) {
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
        this.tickets = tickets;
        this.id = id;
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
     * Getter for location.
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for DateTime.
     *
     * @return
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Getter for tickets.
     *
     * @return
     */
    public int getTickets() {
        return tickets;
    }

    /**
     * Getter for ID.
     *
     * @return
     */
    public int getId() {
        return id;
    }
}
