package utilities;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


/**
 * CS601 Side Project, Expenses Manager Application
 * Utility class for instantiating template Engine
 *
 * @author Aneesh Madhavan
 */
public class TemplateUtil {

    private static final TemplateEngine templateEngine;

    /**
     * Constructor for class.
     */
    private TemplateUtil() {
    }

    static {
        templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.HTML); // HTML5 option was deprecated in 3.0.0
        templateEngine.setTemplateResolver(resolver);
    }

    /**
     * Getter for template Engine.
     *
     * @return
     */
    public static TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
