package CS601.sideproject;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import utilities.Analyzer;
import utilities.ClientInfo;

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

import static CS601.sideproject.ServerConstants.CLIENT_INFO_KEY;
import static utilities.SqlConstants.SELECT_ANALYZER_INFO;
import static utilities.TemplateUtil.getTemplateEngine;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the Spend Analyzer Servlet.
 *
 * @author Aneesh Madhavan
 */
public class SpendAnalyzerServlet extends HttpServlet {
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
        List<Analyzer> analyzer = new ArrayList<>();
        ClientInfo clientInfo = (ClientInfo) req.getSession().getAttribute(CLIENT_INFO_KEY);
        if (clientInfo == null) {
            resp.sendRedirect("/slack");
            return;
        }

        try ( Connection con = DBCPDataSource.getConnection()) {

            ResultSet rs1;
            try (PreparedStatement statement1 = con.prepareStatement(SELECT_ANALYZER_INFO)) {
                statement1.setString(1, clientInfo.getEmail());
                rs1 = statement1.executeQuery();


                while (rs1.next()) {
                    analyzer.add(new Analyzer(rs1.getString("category"), rs1.getDouble("SUM(Amount)")));
                }
                TemplateEngine templateEngine = getTemplateEngine();
                WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
                ctx.setVariable("analyzer", analyzer);
                templateEngine.process("piechart", ctx, resp.getWriter());
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        TemplateEngine templateEngine = getTemplateEngine();
        WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        ctx.setVariable("analyzer", analyzer);
        templateEngine.process("piechart", ctx, resp.getWriter());

    }
}
