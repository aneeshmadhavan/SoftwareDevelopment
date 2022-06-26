package CS601.sideproject;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import utilities.ClientInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static CS601.sideproject.ServerConstants.CLIENT_INFO_KEY;
import static utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the Income Logging Servlet.
 *
 * @author Aneesh Madhavan
 */
public class IncomeServlet extends HttpServlet {
    /**
     * Method that handles GET Requests.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine templateEngine = getTemplateEngine();
        WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(CLIENT_INFO_KEY);
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }
        ctx.setVariable("name", clientInfo.getName());
        templateEngine.process("income", ctx, resp.getWriter());
    }
}
