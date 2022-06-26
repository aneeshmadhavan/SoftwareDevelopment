package CS601.project3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HtmlValidatorTest {

    @Test
    public void isValidTrueTest() {
        String string = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Enter Review Search Term</h1>\n" +
                "\n" +
                "<form action=\"/reviewsearch\" method=\"post\">\n" +
                "  <label for=\"fname\">Search:</label>\n" +
                "  <input type=\"text\" id=\"query\" name=\"query\"/><br/><br/>\n" +
                "  <button type=\"submit\">Submit </button>\n" +
                "</form>\n" +
                "\n" +
                "<h1>Reviews with Term :</h1>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        assertTrue(HtmlValidator.isValid(string));
    }

    @Test
    public void isValidFalseTest() {
        String string = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Enter Review Search Term\n" +
                "\n" +
                "<form action=\"/reviewsearch\" method=\"post\">\n" +
                "  <label for=\"fname\">Search:</label>\n" +
                "  <input type=\"text\" id=\"query\" name=\"query\"/><br/><br/>\n" +
                "  <button type=\"submit\">Submit </button>\n" +
                "\n" +
                "<h1>Reviews with Term :</h1>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        assertFalse(HtmlValidator.isValid(string));
    }
}
