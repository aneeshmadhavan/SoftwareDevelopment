package cs601.project4;

import cs601.utilities.ClientInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static cs601.project4.ServerConstants.ERROR_MESSAGE;
import static cs601.project4.ServerConstants.SUCCESS_MESSAGE;
import static cs601.utilities.SqlConstants.*;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the Ticket Purchase Servlet.
 *
 * @author Aneesh Madhavan
 */
public class TicketPurchaseServlet extends HttpServlet {
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
        } else {
            resp.sendRedirect("/");
        }
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
        int eventId = Integer.parseInt(req.getParameter("event_id"));
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(ServerConstants.CLIENT_INFO_KEY);
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }
        int noOfTickets = Integer.parseInt(req.getParameter("tickets"));

        try (Connection con = DBCPDataSource.getConnection()) {
            ResultSet rs0;
            try (PreparedStatement statement0 = con.prepareStatement(SELECT_ALL_FROM_USER_WITH_EMAIL)) {
                statement0.setString(1, clientInfo.getEmail());
                rs0 = statement0.executeQuery();

                if (!rs0.next()) {
                    resp.sendRedirect("/slack");
                }

                ResultSet rs1;
                try (PreparedStatement statement1 = con.prepareStatement(SELECT_ALL_FROM_EVENTS_WITH_ID)) {
                    statement1.setInt(1, eventId);
                    rs1 = statement1.executeQuery();

                    if (rs1.next()) {
                        int remaining = rs1.getInt("capacity") - noOfTickets;
                        if (remaining < 0) {
                            req.getSession().setAttribute(ERROR_MESSAGE, "Insufficient amount of tickets");
                            resp.sendRedirect("/event?id=" + eventId);
                            return;
                        }

                    }
                    ResultSet rs2;
                    try (PreparedStatement statement2 = con.prepareStatement(SELECT_ALL_FROM_TRANSACTIONS_WITH_EVENTID_AND_USERID)) {
                        statement2.setInt(1, eventId);
                        statement2.setString(2, clientInfo.getEmail());

                        rs2 = statement2.executeQuery();

                        if (rs2.next()) {
                            try (PreparedStatement statement3 = con.prepareStatement(UPDATE_TRANSACTIONS_ADD_TICKETS)) {
                                statement3.setInt(1, noOfTickets);
                                statement3.setInt(2, rs2.getInt("event_id"));
                                statement3.setInt(3, rs2.getInt("user_id"));
                                statement3.executeUpdate();
                            }
                        } else {
                            int userId = rs0.getInt("id");
                            try (PreparedStatement statement4 = con.prepareStatement(INSERT_INTO_TRANSACTIONS)) {
                                statement4.setInt(1, noOfTickets);
                                statement4.setInt(2, userId);
                                statement4.setInt(3, eventId);
                                statement4.executeUpdate();
                            }
                        }

                        try (PreparedStatement statement5 = con.prepareStatement(UPDATE_EVENTS_REDUCE_CAPACITY)) {
                            statement5.setInt(1, noOfTickets);
                            statement5.setInt(2, eventId);
                            statement5.executeUpdate();
                        }

                    }
                }
            }
            req.getSession().setAttribute(SUCCESS_MESSAGE, "Tickets Purchased Successfully!");
            resp.sendRedirect("/event?id=" + eventId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
