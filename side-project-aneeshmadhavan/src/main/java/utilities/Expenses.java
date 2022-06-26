package utilities;


/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the Expenses Object.
 *
 * @author Aneesh Madhavan
 */
public class Expenses {

    private String description;
    private double amount;
    private String category;

    /**
     * Constructor for class.
     *
     * @param description
     * @param amount
     * @param category
     */
    public Expenses(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
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
     * Getter for amount.
     *
     * @return
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Getter for category.
     *
     * @return
     */
    public String getCategory() {
        return category;
    }
}
