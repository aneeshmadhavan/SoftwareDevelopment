package cs601.project4;

import cs601.utilities.ClientInfo;
import cs601.utilities.Config;
import cs601.utilities.HTTPFetcher;
import cs601.utilities.LoginUtilities;
import org.eclipse.jetty.http.HttpStatus;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static cs601.utilities.TemplateUtil.getTemplateEngine;

/**
 * Implements logic for the /login path where Slack will redirect requests after
 * the user has entered their auth info.
 * Taken from code examples.
 */
public class LoginServlet extends HttpServlet {
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

        // retrieve the code provided by Slack
        String code = req.getParameter(ServerConstants.CODE_KEY);

        // generate the url to use to exchange the code for a token:
        // After the user successfully grants your app permission to access their Slack profile,
        // they'll be redirected back to your service along with the typical code that signifies
        // a temporary access code. Exchange that code for a real access token using the
        // /openid.connect.token method.
        String url = LoginUtilities.generateSlackTokenURL(config.getClient_id(), config.getClient_secret(), code, config.getRedirect_url());

        // Make the request to the token API
        String responseString = HTTPFetcher.doGet(url, null);
        Map<String, Object> response = LoginUtilities.jsonStrToMap(responseString);
        

        ClientInfo clientInfo = LoginUtilities.verifyTokenResponse(response, sessionId);

        if (clientInfo == null) {
            resp.setStatus(HttpStatus.OK_200);
            TemplateEngine templateEngine = getTemplateEngine();
            WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
            templateEngine.process("LoginFailed", ctx, resp.getWriter());
        } else {
            req.getSession().setAttribute(ServerConstants.CLIENT_INFO_KEY, clientInfo);
            resp.setStatus(HttpStatus.OK_200);
            resp.sendRedirect("/user");
        }
    }
}