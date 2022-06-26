package cs601.utilities;

import java.util.Date;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the Events object.
 *
 * @author Aneesh Madhavan
 */
public class Events {

    private final int id;
    private final String name;
    private final String description;
    private final Date dateTime;
    private final double price;
    private final String location;
    private final String category;
    private final int createdby;
    private final int capacity;

    /**
     * Constructor for class.
     *
     * @param id
     * @param name
     * @param description
     * @param dateTime
     * @param price
     * @param location
     * @param category
     * @param createdby
     * @param capacity
     */
    public Events(int id, String name, String description, Date dateTime,
                  double price, String location, String category,
                  int createdby, int capacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.price = price;
        this.location = location;
        this.category = category;
        this.createdby = createdby;
        this.capacity = capacity;
    }

    /**
     * Getter for id.
     *
     * @return
     */
    public int getId() {
        return id;
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
     * Getter for description.
     *
     * @return
     */
    public String getDescription() {
        return description;
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
     * Getter for price.
     *
     * @return
     */
    public double getPrice() {
        return price;
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
     * Getter for category.
     *
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     * Getter for createdby.
     *
     * @return
     */
    public int getCreatedby() {
        return createdby;
    }

    /**
     * Getter for capacity.
     *
     * @return
     */
    public int getCapacity() {
        return capacity;
    }


}
