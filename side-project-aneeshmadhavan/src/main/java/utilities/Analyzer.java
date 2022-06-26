package utilities;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the Analyzer Object.
 *
 * @author Aneesh Madhavan
 */
public class Analyzer {

    private String category;
    private Double amount;

    /**
     * Constructor for class.
     *
     * @param category
     * @param amount
     */
    public Analyzer(String category, Double amount) {
        this.category = category;
        this.amount = amount;
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
     * Getter for name.
     *
     * @return
     */
    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "[ " + "'" + getCategory() + "'" + ", " + getAmount() + " ]";
    }
}
