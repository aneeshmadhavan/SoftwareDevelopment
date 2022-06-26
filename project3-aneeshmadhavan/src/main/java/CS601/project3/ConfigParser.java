package CS601.project3;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that parses the config File.
 *
 * @author Aneesh Madhavan
 */
public class ConfigParser {

    static Config config;

    /**
     * Private constructor for Utility class.
     */
    private ConfigParser() {
    }

    /**
     * Method that parses the input config filename.
     *
     * @param filename
     * @throws FileNotFoundException
     */
    public static void parseConfig(String filename) throws FileNotFoundException {
        Gson gson = new Gson();
        config = gson.fromJson(new FileReader(filename), Config.class);
    }
}
