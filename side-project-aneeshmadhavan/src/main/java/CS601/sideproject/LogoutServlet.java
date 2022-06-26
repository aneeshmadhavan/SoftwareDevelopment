package CS601.sideproject;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Handles a request to sign out.
 * Taken from code examples.
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // log out by invalidating the session
        req.getSession().invalidate();
        resp.getWriter().println(ServerConstants.PAGE_HEADER);
        resp.getWriter().println("<h1>Bye!</h1>");
        resp.getWriter().println(ServerConstants.PAGE_FOOTER);

    }
}