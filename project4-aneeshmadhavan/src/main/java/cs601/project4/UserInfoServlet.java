package cs601.project4;

import cs601.utilities.ClientInfo;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static cs601.utilities.SqlConstants.INSERT_INTO_USER;
import static cs601.utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the User Info Servlet.
 *
 * @author Aneesh Madhavan
 */
public class UserInfoServlet extends HttpServlet {

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
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }
        TemplateEngine templateEngine = getTemplateEngine();
        WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        ctx.setVariable("name", clientInfo.getName());
        templateEngine.process("userinfo", ctx, resp.getWriter());
    }

    /**
     * Method that handles POST requests.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(ServerConstants.CLIENT_INFO_KEY);
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }
        int age = Integer.parseInt(req.getParameter("age"));
        String phone = req.getParameter("phone");

        try (Connection con = DBCPDataSource.getConnection()) {
            try (PreparedStatement statement1 = con.prepareStatement(INSERT_INTO_USER)) {
                statement1.setString(1, clientInfo.getName());
                statement1.setString(2, clientInfo.getEmail());
                statement1.setInt(3, age);
                statement1.setString(4, phone);
                statement1.executeUpdate();
            }
            resp.sendRedirect("user");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
