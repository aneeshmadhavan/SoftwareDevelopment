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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cs601.project4.ServerConstants.SUCCESS_MESSAGE;
import static cs601.utilities.SqlConstants.INSERT_INTO_EVENTS;
import static cs601.utilities.SqlConstants.SELECT_ALL_FROM_USER_WITH_EMAIL;
import static cs601.utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the Event creation Servlet.
 *
 * @author Aneesh Madhavan
 */
public class CreateEventServlet extends HttpServlet {
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
        String success = (String) req.getSession().getAttribute(SUCCESS_MESSAGE);
        TemplateEngine templateEngine = getTemplateEngine();
        WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        ctx.setVariable(SUCCESS_MESSAGE, success);
        templateEngine.process("createevent", ctx, resp.getWriter());
        req.getSession().removeAttribute(SUCCESS_MESSAGE);
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

        try (Connection con = DBCPDataSource.getConnection()) {
            ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(ServerConstants.CLIENT_INFO_KEY);
            if (clientInfo == null) {
                resp.sendRedirect("/slack");
                return;
            }
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String location = req.getParameter("location");
            String category = req.getParameter("category");
            double price = Double.parseDouble(req.getParameter("price"));
            int capacity = Integer.parseInt(req.getParameter("capacity"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date = simpleDateFormat.parse(req.getParameter("datetime").replace("T", " "));

            ResultSet rs1;
            try (PreparedStatement statement1 = con.prepareStatement(SELECT_ALL_FROM_USER_WITH_EMAIL)) {
                statement1.setString(1, clientInfo.getEmail());
                rs1 = statement1.executeQuery();

                if (!rs1.next()) {
                    resp.sendRedirect("/slack");
                    return;
                }
                int createdby = rs1.getInt("id");
                try (PreparedStatement statement2 = con.prepareStatement(INSERT_INTO_EVENTS)) {
                    statement2.setString(1, name);
                    statement2.setString(2, description);
                    statement2.setTimestamp(3, new Timestamp(date.getTime()));
                    statement2.setDouble(4, price);
                    statement2.setString(5, location);
                    statement2.setString(6, category);
                    statement2.setInt(7, createdby);
                    statement2.setInt(8, capacity);
                    statement2.executeUpdate();

                }
            }

            req.getSession().setAttribute(SUCCESS_MESSAGE, "Event Created!");
            resp.sendRedirect("/event/create");
        } catch (ParseException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
