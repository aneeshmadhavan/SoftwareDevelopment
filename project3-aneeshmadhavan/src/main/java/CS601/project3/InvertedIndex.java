package CS601.project3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains the Inverted Index data structure
 * and some related searching methods, modified from Project 1.
 *
 * @author Aneesh Madhavan.
 */
public class InvertedIndex {

    private final Map<String, Map<Integer, Integer>> invertedIndexMap = new HashMap<>();



    /**
     * Method that processes the String by removing
     * non alpha-numeric characters.
     *
     * @param text String to be tokenized.
     * @return String array with terms split on white spaces.
     */
    private String[] tokenizeString(String text) {
        text = text.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase(Locale.ROOT);
        return text.split(" ");
    }

    /**
     * Method that adds elements to the Inverted Index.
     *
     * @param text String containing values to be added to the Inverted Index.
     * @param docId Index of document in the object List.
     */
    public synchronized void addToIndex(String text, int docId) {
        String[] result = tokenizeString(text);
        for (String s : result) {
            Map<Integer, Integer> map = invertedIndexMap.getOrDefault(s, new HashMap<>());
            map.put(docId, map.getOrDefault(docId, 0) + 1);
            invertedIndexMap.put(s, map);
        }
    }

    /**
     * Method that searches the Inverted Index
     * for the given input term.
     *
     * @param term term to be searched.
     * @return map containing the Document ID and
     * the number of occurrences of the term in the
     * document.
     */
    public Map<Integer, Integer> searchIndex(String term) {
        Map<Integer, Integer> map;
        if (!term.isEmpty() && (map = invertedIndexMap
                .get(term.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(Locale.ROOT))) != null) {
            return map;
        } else {
            return Collections.emptyMap();
        }
    }
}
