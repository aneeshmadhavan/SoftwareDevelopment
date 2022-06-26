package CS601.sideproject;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import utilities.ClientInfo;
import utilities.Expenses;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static CS601.sideproject.ServerConstants.CLIENT_INFO_KEY;
import static CS601.sideproject.ServerConstants.ERROR_MESSAGE;
import static CS601.sideproject.ServerConstants.WARNING;
import static utilities.SqlConstants.INSERT_INTO_USER;
import static utilities.SqlConstants.SELECT_ALL_FROM_EXPENSES_WITH_EMAIL;
import static utilities.SqlConstants.SELECT_INCOME_WITH_EMAIL;
import static utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the User Data Servlet.
 *
 * @author Aneesh Madhavan
 */

public class DataServlet extends HttpServlet {

    /**
     * Method that handles GET Requests.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(CLIENT_INFO_KEY);
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }

        List<Expenses> expenses = new ArrayList<>();
        String errorMessage = null;


      try (Connection con = DBCPDataSource.getConnection()) {
            ResultSet rs1;
            try (PreparedStatement statement1 = con.prepareStatement(SELECT_INCOME_WITH_EMAIL)) {
                statement1.setString(1, clientInfo.getEmail());
                rs1 = statement1.executeQuery();

            if (!rs1.next()) {
                resp.sendRedirect("/income");
                return;
            } else {
                ResultSet rs2;
              try (PreparedStatement statement2 = con.prepareStatement(SELECT_ALL_FROM_EXPENSES_WITH_EMAIL)) {
                    statement2.setString(1, clientInfo.getEmail());
                    rs2 = statement2.executeQuery();

                 while (rs2.next()) {
                     expenses.add(new Expenses(rs2.getString("description"), rs2.getDouble("amount"), rs2.getString("category")));
                 }
                 TemplateEngine templateEngine = getTemplateEngine();
                  WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
                   Double income = rs1.getDouble("Income");
                  if (income < 50.0) {
                       req.getSession().setAttribute(ERROR_MESSAGE, WARNING);
                        errorMessage = (String) req.getSession().getAttribute(ERROR_MESSAGE);
                    }
                   ctx.setVariable("income", income);
                   ctx.setVariable(ERROR_MESSAGE, errorMessage);
                   ctx.setVariable("expenses", expenses);
                   ctx.setVariable("name", clientInfo.getName());
                   templateEngine.process("data", ctx, resp.getWriter());
                   req.getSession().removeAttribute(ERROR_MESSAGE);
             }
             }
         }

     } catch (SQLException e) {
          System.err.println(e.getMessage());
     }

 }

    /**
     * Method that Handles Post Requests.
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
        try (Connection con = DBCPDataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(INSERT_INTO_USER)) {
                statement.setString(1, clientInfo.getName());
                statement.setString(2, clientInfo.getEmail());
                statement.setDouble(3, Double.parseDouble(req.getParameter("income")));
                statement.executeUpdate();
            }
            resp.sendRedirect("/data");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
