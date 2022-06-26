package cs601.project4;

import cs601.utilities.Events;
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

import static cs601.utilities.SqlConstants.SELECT_ALL_FROM_EVENTS;
import static cs601.utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the Home Page Servlet.
 *
 * @author Aneesh Madhavan
 */
public class HomePageServlet extends HttpServlet {

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
        // determine whether the user is already authenticated
        Object clientInfoObj = req.getSession().getAttribute(ServerConstants.CLIENT_INFO_KEY);
        if (clientInfoObj != null) {
            // already authed, no need to log in
            resp.sendRedirect("/user");
            return;
        }
        List<Events> events = new ArrayList<>();
        try (Connection con = DBCPDataSource.getConnection()) {

            ResultSet rs1;
            try (PreparedStatement statement1 = con.prepareStatement(SELECT_ALL_FROM_EVENTS)) {
                rs1 = statement1.executeQuery();

                while (rs1.next()) {
                    events.add(new Events(rs1.getInt("id"), rs1.getString("name"),
                            rs1.getString("description"), rs1.getTimestamp("datetime"),
                            rs1.getDouble("price"), rs1.getString("location"),
                            rs1.getString("category"), rs1.getInt("createdby"),
                            rs1.getInt("capacity")));
                }
            }
            TemplateEngine templateEngine = getTemplateEngine();
            WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
            ctx.setVariable("events", events);
            templateEngine.process("home", ctx, resp.getWriter());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
