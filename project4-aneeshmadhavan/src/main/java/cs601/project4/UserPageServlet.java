package cs601.project4;

import cs601.utilities.ClientInfo;
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
import static cs601.utilities.SqlConstants.SELECT_ALL_FROM_USER_WITH_EMAIL;
import static cs601.utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the User Page Servlet.
 *
 * @author Aneesh Madhavan
 */
public class UserPageServlet extends HttpServlet {

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
        List<Events> events = new ArrayList<>();
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }

        try (Connection con = DBCPDataSource.getConnection()) {
            ResultSet rs1;
            try (PreparedStatement statement1 = con.prepareStatement(SELECT_ALL_FROM_USER_WITH_EMAIL)) {
                statement1.setString(1, clientInfo.getEmail());
                rs1 = statement1.executeQuery();

                if (!rs1.next()) {
                    resp.sendRedirect("/userinfo");
                    return;

                } else {
                    ResultSet rs2;
                    try (PreparedStatement statement2 = con.prepareStatement(SELECT_ALL_FROM_EVENTS)) {
                        rs2 = statement2.executeQuery();

                        while (rs2.next()) {
                            events.add(new Events(rs2.getInt("id"), rs2.getString("name"),
                                    rs2.getString("description"), rs2.getTimestamp("datetime"),
                                    rs2.getDouble("price"), rs2.getString("location"),
                                    rs2.getString("category"), rs2.getInt("createdby"),
                                    rs2.getInt("capacity")));
                        }
                        TemplateEngine templateEngine = getTemplateEngine();
                        WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
                        ctx.setVariable("name", rs1.getString("name"));
                        ctx.setVariable("events", events);
                        templateEngine.process("userpage", ctx, resp.getWriter());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

