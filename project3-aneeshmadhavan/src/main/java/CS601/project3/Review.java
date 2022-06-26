package CS601.project3;

import java.util.ArrayList;
import java.util.List;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains the Review file object,
 * taken for Project1.
 *
 * @author Aneesh Madhavan.
 */
public class Review extends Data {

    private String reviewerID;
    private String reviewerName;
    private ArrayList<Integer> helpful;
    private String reviewText;
    private double overall;
    private String summary;
    private long unixReviewTime;
    private String reviewTime;

    /**
     * Constructor for the class.
     *
     * @param reviewerID
     * @param asin
     * @param reviewerName
     * @param helpful
     * @param reviewText
     * @param overall
     * @param summary
     * @param unixReviewTime
     * @param reviewTime
     */
    public Review(String reviewerID, String asin, String reviewerName,
                  ArrayList<Integer> helpful, String reviewText, double overall,
                  String summary, long unixReviewTime, String reviewTime) {
        this.reviewerID = reviewerID;
        this.asin = asin;
        this.reviewerName = reviewerName;
        this.helpful = helpful;
        this.reviewText = reviewText;
        this.overall = overall;
        this.summary = summary;
        this.unixReviewTime = unixReviewTime;
        this.reviewTime = reviewTime;
    }

    /**
     * Default constructor for class.
     */
    public Review() {
    }

    /**
     * Getter for reviewerID.
     *
     * @return
     */
    public String getReviewerID() {
        return reviewerID;
    }

    /**
     * Getter for reviewerName.
     *
     * @return
     */
    public String getReviewerName() {
        return reviewerName;
    }

    /**
     * Getter for helpful.
     *
     * @return
     */
    public List<Integer> getHelpful() {
        return helpful;
    }

    /**
     * Getter for reviewText.
     *
     * @return
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Getter for overall.
     *
     * @return
     */
    public double getOverall() {
        return overall;
    }

    /**
     * Getter for Summary.
     *
     * @return
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Getter for reviewTime.
     *
     * @return
     */
    public String getReviewTime() {
        return reviewTime;
    }

    /**
     * toString method for the object.
     * @return
     */
    @Override
    public String toString() {
        return "Review{" +
                "reviewerID='" + reviewerID + '\'' +
                ", asin='" + asin + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }

}
