package cs601.utilities;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains MySQL Constants.
 *
 * @author Aneesh Madhavan
 */
public class SqlConstants {
    public static final String INSERT_INTO_USER = "INSERT INTO user (name, email, age, phone) values(?, ?, ?, ?) ";
    public static final String SELECT_ALL_FROM_USER_WITH_EMAIL = "SELECT * FROM user WHERE Email = ?";
    public static final String INSERT_INTO_EVENTS = "INSERT INTO events (name, description, datetime, price, location, " +
            "category, createdby, capacity)" +
            " values(?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_ALL_FROM_EVENTS = "SELECT * from events";
    public static final String SELECT_ALL_FROM_EVENTS_WITH_ID = "SELECT * from events WHERE id = ?";
    public static final String SELECT_ALL_FROM_EVENTS_WITH_TERM = "SELECT * from events where name like CONCAT( '%',?,'%') " +
            "or description like CONCAT( '%',?,'%') " +
            "or location like CONCAT( '%',?,'%') " +
            "or category like CONCAT( '%',?,'%')";
    public static final String SELECT_ALL_FROM_TRANSACTIONS_WITH_EVENTID_AND_USERID = "SELECT * from transactions WHERE event_id = ? " +
            "and user_id = (SELECT id FROM user WHERE Email = ?) ";
    public static final String UPDATE_TRANSACTIONS_ADD_TICKETS = "Update transactions SET tickets = tickets + ? WHERE event_id = ? " +
            "and user_id = ?";
    public static final String UPDATE_TRANSACTIONS_REDUCE_TICKETS = "Update transactions SET tickets = tickets - ? WHERE event_id = ? " +
            "and user_id = (Select id from user where email = ?)";
    public static final String INSERT_INTO_TRANSACTIONS = "INSERT INTO transactions (tickets, user_id, event_id) values(?, ?, ?) ";
    public static final String UPDATE_EVENTS_REDUCE_CAPACITY = "Update events SET capacity = capacity - ? Where id = ?";
    public static final String SELECT_TRANSACTION_OBJECT_WITH_USERID = "SELECT transactions.tickets, events.id, events.name, events.location, events.datetime " +
            "from transactions JOIN events on events.id = transactions.event_id " +
            "WHERE transactions.user_id = (Select id from user where email = ?)";
    public static final String UPDATE_USER_SET_NAME = "Update user SET name = ? Where user.id = (Select id from user Where Email = ?)";
    public static final String UPDATE_USER_SET_AGE = "Update user SET age = ? Where user.id = (Select id from user Where Email = ?)";
    public static final String UPDATE_USER_SET_PHONE = "Update user SET phone = ? Where user.id = (Select id from user Where Email = ?)";

}
