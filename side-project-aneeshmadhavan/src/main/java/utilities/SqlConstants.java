package utilities;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the MySQL constants.
 *
 * @author Aneesh Madhavan
 */
public class SqlConstants {
    public static final String SELECT_INCOME_WITH_EMAIL = "SELECT Income FROM user WHERE Email = ? ";
    public static final String SELECT_ALL_FROM_EXPENSES_WITH_EMAIL = "Select * FROM expenses WHERE Email = ?";
    public static final String INSERT_INTO_EXPENSES = "INSERT INTO expenses (Email, Description, Amount, category) values(?, ?, ?, ?) ";
    public static final String INSERT_INTO_USER = "INSERT INTO user values(?, ?, ?) ";
    public static final String UPDATE_USER_REDUCE_INCOME = "UPDATE user SET user.Income = user.Income - ? " +
            "WHERE user.Email = ?";
    public static final String UPDATE_USER_ADD_INCOME = "Update user set income = income + ? where email = ?";
    public static final String SELECT_ANALYZER_INFO = "SELECT category, SUM(Amount) FROM expenses WHERE Email = ? GROUP BY category";
}
