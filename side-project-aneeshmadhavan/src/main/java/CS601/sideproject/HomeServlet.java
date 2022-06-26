package CS601.sideproject;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the Home Page Servlet.
 *
 * @author Aneesh Madhavan
 */
public class HomeServlet extends HttpServlet {
    /**
     * Method that Handles GET Requests.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object clientInfoObj = req.getSession().getAttribute(ServerConstants.CLIENT_INFO_KEY);
        if (clientInfoObj != null) {
            // already authed, no need to log in
            resp.sendRedirect("/data");
            return;
        }
        TemplateEngine templateEngine = getTemplateEngine();
        WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        templateEngine.process("home", ctx, resp.getWriter());
    }
}
