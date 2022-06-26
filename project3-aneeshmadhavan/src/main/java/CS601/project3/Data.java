package CS601.project3;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Modified Data class from Project1.
 *
 * @author Aneesh Madhavan
 */
public class Data {

    private static final Logger LOGGER = LogManager.getLogger(Data.class);
    protected String asin;

    /**
     * Getter for asin.
     *
     * @return
     */
    public String getAsin() {
        return asin;
    }

    /**
     * Class that parses the file into a List of objects.
     *
     * @param clazz Class name on which the method has to operate.
     * @param filename Name of file to be processed.
     * @param <T> Generic class.
     * @return List of objects for the input class.
     */
    public static <T> List<T> parseData(Class<T> clazz, String filename) {
        List<T> list = new ArrayList<>();
        Gson gson = new Gson();
        String line;
        try (BufferedReader reviewReader = new BufferedReader(new FileReader(filename,
                StandardCharsets.ISO_8859_1))) {
            while ((line = reviewReader.readLine()) != null) {
                try {
                    list.add(gson.fromJson(line, clazz));
                } catch (JsonSyntaxException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            System.exit(1);
        }
        return list;
    }

    /**
     * Method that returns a list of
     * Reviews / Q&A's from the
     * respective files containing the input asin.
     *
     * @param list List of objects to be searched.
     * @param asin String item to be searched in the list.
     * @return newList
     */
    public static <T> List findAsin(List<T> list, String asin) {
        List newList = new ArrayList<>();
        for (Object o : list) {
            if (((Data) o).getAsin().equals(asin)) {
                newList.add(o);
            }
        }
        return newList;
    }

    /**
     * Method that sorts the Map in
     * descending order of values.
     *
     * @param map Map to be sorted.
     * @return List containing keys of the sorted map.
     */
    public static List<Integer> sortMap(Map<Integer, Integer> map) {
        List<Integer> list = new ArrayList<>();
        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(pair -> sortedMap.put(pair.getKey(), pair.getValue()));
        for (int key : sortedMap.keySet()) {
                list.add(key);
        }
        return list;
    }

    /**
     * Method that returns a list of objects.
     *
     * @param clazzList List of objects to be searched.
     * @param list List containing indices to be searched.
     * @param <T> Generic class.
     * @return newList
     */
    public static <T> List findData(List<T> clazzList, List<Integer> list) {
        List newlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newlist.add(clazzList.get(list.get(i)).toString());
        }
        return newlist;
    }
}
