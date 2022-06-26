package CS601.project3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Helper class with method to determine if an HTML document is well-formed,
 * taken from code examples.
 */
public class HtmlValidator {
    private static final Logger LOGGER = LogManager.getLogger(HtmlValidator.class);


    /**
     * Private constructor for Utility class
     */
    private HtmlValidator() {
    }

    /**
     * Returns true if the html is valid and false otherwise.
     * Taken from Wellformed.java example from
     * http://www.edankert.com/validate.html#Sample_Code
     *
     * @param html
     * @return
     */
    public static boolean isValid(String html) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.parse(new InputSource(new StringReader(html)));
            return true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }


}
