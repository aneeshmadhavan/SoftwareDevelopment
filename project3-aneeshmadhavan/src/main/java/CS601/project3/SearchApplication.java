package CS601.project3;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains the Search Application.
 *
 * @author Aneesh Madhavan
 */
public class SearchApplication {

    public static List<Review> reviewList;
    public static List<Qa> qaList;
    public static InvertedIndex reviewInvertedIndex;

    /**
     * Main method for Search Application.
     *
     * @param args
     */
    public static void main(String[] args) {

            try {
                ConfigParser.parseConfig("config.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        initDataStructures();
        HttpServer server = new HttpServer(8080);
        server.addMapping("/find", new FindHandler());
        server.addMapping("/reviewsearch", new ReviewSearchHandler());
        server.addMapping("/shutdown", new ShutdownHandler(server));
        server.startServer();
    }

    public static void initDataStructures() {
        reviewList = Data.parseData(Review.class, ConfigParser.config.getReviewFile());
        reviewInvertedIndex = new InvertedIndex();
        for (int i = 0; i < reviewList.size(); i++) {
            reviewInvertedIndex.addToIndex(reviewList.get(i).getReviewText(), i);
        }
        qaList = Data.parseData(Qa.class, ConfigParser.config.getQaFile());
    }
}
