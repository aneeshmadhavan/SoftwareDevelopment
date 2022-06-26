package CS601.sideproject;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import utilities.ClientInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static CS601.sideproject.ServerConstants.CLIENT_INFO_KEY;
import static utilities.SqlConstants.INSERT_INTO_EXPENSES;
import static utilities.SqlConstants.UPDATE_USER_REDUCE_INCOME;
import static utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the Expense Logging Servlet.
 *
 * @author Aneesh Madhavan
 */
public class ExpensesServlet extends HttpServlet {

    /**
     * Method that handles GET Requests.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(CLIENT_INFO_KEY);
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }
        TemplateEngine templateEngine = getTemplateEngine();
        WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        templateEngine.process("expenses", ctx, resp.getWriter());

    }

    /**
     * Method that Handles POST Requests.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(CLIENT_INFO_KEY);
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }
        double amount = Double.parseDouble(req.getParameter("amount"));
        String category = req.getParameter("category");


        try (Connection con = DBCPDataSource.getConnection();) {

            try (PreparedStatement statement1 = con.prepareStatement(INSERT_INTO_EXPENSES)) {
                statement1.setString(1, clientInfo.getEmail());
                statement1.setString(2, req.getParameter("description"));
                statement1.setDouble(3, amount);
                statement1.setString(4, category);
                statement1.executeUpdate();
            }

            try (PreparedStatement statement2 = con.prepareStatement(UPDATE_USER_REDUCE_INCOME)) {
                statement2.setDouble(1, amount);
                statement2.setString(2, clientInfo.getEmail());
                statement2.executeUpdate();
            }
            resp.sendRedirect("/data");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
