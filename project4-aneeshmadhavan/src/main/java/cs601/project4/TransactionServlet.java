package cs601.project4;

import cs601.utilities.ClientInfo;
import cs601.utilities.Transactions;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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

import static cs601.utilities.SqlConstants.SELECT_TRANSACTION_OBJECT_WITH_USERID;
import static cs601.utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the Transaction Servlet.
 *
 * @author Aneesh Madhavan
 */
public class TransactionServlet extends HttpServlet {

    /**
     * Method that handles GET requests.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(ServerConstants.CLIENT_INFO_KEY);
        List<Transactions> transactions = new ArrayList<>();
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }

        try (Connection con = DBCPDataSource.getConnection()) {
            ResultSet rs2;
            try (PreparedStatement statement2 = con.prepareStatement(SELECT_TRANSACTION_OBJECT_WITH_USERID)) {
                statement2.setString(1, clientInfo.getEmail());
                rs2 = statement2.executeQuery();

                while (rs2.next()) {
                    transactions.add(new Transactions(rs2.getString("name"), rs2.getString("location"),
                            rs2.getTimestamp("datetime"), rs2.getInt("tickets"),
                            rs2.getInt("id")));
                }
                TemplateEngine templateEngine = getTemplateEngine();
                WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
                ctx.setVariable("transactions", transactions);
                templateEngine.process("viewtransactions", ctx, resp.getWriter());

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}