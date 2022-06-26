package CS601.project3;

import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Map;

import static CS601.project3.SearchApplication.qaList;
import static CS601.project3.SearchApplication.reviewInvertedIndex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static CS601.project3.SearchApplication.initDataStructures;
import static CS601.project3.SearchApplication.reviewList;

public class IntegrationTests {

    @Test
    void parseInputFileAndFindAsinInBothFiles() {
        ConfigParser.config = new Config("Reviews.json", "Qas.json", "", "");
        initDataStructures();
        List list = Data.findAsin(reviewList, "B0002OKCXE");
        assertEquals(29, list.size());
        list = Data.findAsin(qaList, "B0002OKCXE");
        assertEquals(9, list.size());
    }

    @Test
    void parseInputFileAndFindAsinOnlyInReviewsTest() {
        ConfigParser.config = new Config("Reviews.json", "Qas.json", "", "");
        initDataStructures();
        List list = Data.findAsin(reviewList, "B000982UY2");
        assertEquals(7, list.size());
        list = Data.findAsin(qaList, "B000982UY2");
        assertEquals(0, list.size());
    }

    @Test
    void parseInputFileAndFindAsinOnlyInQasTest() {
        ConfigParser.config = new Config("Reviews.json", "Qas.json", "", "");
        initDataStructures();
        List list = Data.findAsin(reviewList, "1466736038");
        assertEquals(0, list.size());
        list = Data.findAsin(qaList, "1466736038");
        assertEquals(10, list.size());
    }

    @Test
    void parseInputFileAndFindNonExistingAsinTest() {
        ConfigParser.config = new Config("Reviews.json", "Qas.json", "", "");
        initDataStructures();
        List list = Data.findAsin(reviewList, "aaaaabbbbccccc");
        assertEquals(0, list.size());
        list = Data.findAsin(qaList, "aaaaabbbbccccc");
        assertEquals(0, list.size());
    }

    @Test
    void parseInputFileAndExistingReviewSearchTest() {
        ConfigParser.config = new Config("Reviews.json", "Qas.json", "", "");
        initDataStructures();
        Map<Integer, Integer> reviewMap = reviewInvertedIndex.searchIndex("archaic");
        List<Integer> reviewItems = Data.sortMap(reviewMap);
        List list = Data.findData(reviewList, reviewItems);
        assertEquals(1, list.size());
    }

    @Test
    void parseInputFileAndNonExistingReviewSearchTest() {
        ConfigParser.config = new Config("Reviews.json", "Qas.json", "", "");
        initDataStructures();
        Map<Integer, Integer> reviewMap = reviewInvertedIndex.searchIndex("aaabbbbccccc");
        List<Integer> reviewItems = Data.sortMap(reviewMap);
        List list = Data.findData(reviewList, reviewItems);
        assertEquals(0, list.size());
    }

}
