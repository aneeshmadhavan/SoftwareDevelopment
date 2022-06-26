package cs601.project4;

import cs601.utilities.Config;
import cs601.utilities.LoginUtilities;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Landing page that allows a user to request to login with Slack.
 * Taken from code examples.
 */
public class SlackServlet extends HttpServlet {
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

        // retrieve the ID of this session
        String sessionId = req.getSession(true).getId();

        // determine whether the user is already authenticated
        Object clientInfoObj = req.getSession().getAttribute(ServerConstants.CLIENT_INFO_KEY);
        if (clientInfoObj != null) {
            // already authed, no need to log in
            resp.sendRedirect("/user");
            return;
        }
        // retrieve the config info from the context
        Config config = (Config) req.getServletContext().getAttribute(ServerConstants.CONFIG_KEY);

        /** From the OpenID spec:
         * state
         * RECOMMENDED. Opaque value used to maintain state between the request and the callback.
         * Typically, Cross-Site Request Forgery (CSRF, XSRF) mitigation is done by cryptographically
         * binding the value of this parameter with a browser cookie.
         *
         * Use the session ID for this purpose.
         */
        String state = sessionId;

        /** From the Open ID spec:
         * nonce
         * OPTIONAL. String value used to associate a Client session with an ID Token, and to mitigate
         * replay attacks. The value is passed through unmodified from the Authentication Request to
         * the ID Token. Sufficient entropy MUST be present in the nonce values used to prevent attackers
         * from guessing values. For implementation notes, see Section 15.5.2.
         */
        String nonce = LoginUtilities.generateNonce(state);

        // Generate url for request to Slack
        String url = LoginUtilities.generateSlackAuthorizeURL(config.getClient_id(),
                state,
                nonce,
                config.getRedirect_url());

        resp.setStatus(HttpStatus.OK_200);
        PrintWriter writer = resp.getWriter();

        writer.println(ServerConstants.PAGE_HEADER);
        writer.println("<h1>Login with Slack</h1>");
        writer.println("<a href=\"" + url + "\"><img src=\"" + ServerConstants.BUTTON_URL + "\"/></a>");
        writer.println(ServerConstants.PAGE_FOOTER);
    }
}