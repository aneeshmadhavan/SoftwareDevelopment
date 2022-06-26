package CS601.sideproject;

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
import static utilities.SqlConstants.UPDATE_USER_ADD_INCOME;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the User Data Servlet.
 *
 * @author Aneesh Madhavan
 */

public class UpdateIncomeServlet extends HttpServlet {
    /**
     * Method that handles POST Requests.
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
        Double income = Double.parseDouble(req.getParameter("income"));

        try (Connection con = DBCPDataSource.getConnection()) {
            try (PreparedStatement statement1 = con.prepareStatement(UPDATE_USER_ADD_INCOME)) {
                statement1.setDouble(1, income);
                statement1.setString(2, clientInfo.getEmail());
                statement1.executeUpdate();
            }
            resp.sendRedirect("/data");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }
}
