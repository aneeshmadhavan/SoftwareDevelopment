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
import java.sql.ResultSet;
import java.sql.SQLException;

import static cs601.utilities.SqlConstants.UPDATE_USER_SET_AGE;
import static cs601.utilities.SqlConstants.UPDATE_USER_SET_NAME;
import static cs601.utilities.SqlConstants.UPDATE_USER_SET_PHONE;
import static cs601.utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the Update Info Servlet.
 *
 * @author Aneesh Madhavan
 */
public class UpdateInfoServlet extends HttpServlet {

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

        try (Connection con = DBCPDataSource.getConnection()) {
            String s2 = "Select * from user where id = (Select id from user where email = ?)";
            PreparedStatement statement2 = con.prepareStatement(s2);
            statement2.setString(1, clientInfo.getEmail());
            ResultSet rs2 = statement2.executeQuery();
            if (rs2.next()) {
                TemplateEngine templateEngine = getTemplateEngine();
                WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
                ctx.setVariable("name", rs2.getString("name"));
                ctx.setVariable("email", rs2.getString("email"));
                ctx.setVariable("phone", rs2.getString("phone"));
                ctx.setVariable("age", rs2.getInt("age"));
                templateEngine.process("updateinfo", ctx, resp.getWriter());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
        TemplateEngine templateEngine = getTemplateEngine();
        WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        templateEngine.process("updateinfo", ctx, resp.getWriter());
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
        String age = req.getParameter("age");
        String phone = req.getParameter("phone");
        String name = req.getParameter("name");
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(ServerConstants.CLIENT_INFO_KEY);
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }
        try (Connection con = DBCPDataSource.getConnection()) {

            if (!name.isEmpty()) {
                try (PreparedStatement statement1 = con.prepareStatement(UPDATE_USER_SET_NAME)) {
                    statement1.setString(1, name);
                    statement1.setString(2, clientInfo.getEmail());
                    statement1.executeUpdate();
                }
            }

            if (!age.isEmpty()) {
                try (PreparedStatement statement2 = con.prepareStatement(UPDATE_USER_SET_AGE)) {
                    statement2.setInt(1, Integer.parseInt(age));
                    statement2.setString(2, clientInfo.getEmail());
                    statement2.executeUpdate();
                }
            }

            if (!phone.isEmpty()) {
                try (PreparedStatement statement3 = con.prepareStatement(UPDATE_USER_SET_PHONE)) {
                    statement3.setString(1, phone);
                    statement3.setString(2, clientInfo.getEmail());
                    statement3.executeUpdate();
                }
            }

            resp.sendRedirect("/userinfo/update");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
