package CS601.project3;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ConfigParserTest {

    @Test
    public void testParseConfig() {
        try {
            ConfigParser.parseConfig("config.json");
            assertEquals("C02EBVCT3HA", ConfigParser.config.getChannelId());
            assertEquals("Reviews.json", ConfigParser.config.getReviewFile());
            assertEquals("Qas.json", ConfigParser.config.getQaFile());
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testParseConfigThrowsException() {
        try {
            ConfigParser.parseConfig("missingconfig.json");
            fail();
        } catch (FileNotFoundException e) {
            assertEquals("missingconfig.json (No such file or directory)", e.getMessage());
        }
    }



}
