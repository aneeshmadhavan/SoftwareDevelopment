package CS601.project3;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Base class for config Json.
 *
 * @author Aneesh Madhavan
 */
public class Config {
    private final String reviewFile;
    private final String qaFile;
    private final String channelId;
    private final String slackToken;

    /**
     * Constructor for the class.
     *
     * @param reviewFile
     * @param qaFile
     * @param channelId
     * @param slackToken
     */
    public Config(String reviewFile, String qaFile, String channelId, String slackToken) {
        this.reviewFile = reviewFile;
        this.qaFile = qaFile;
        this.channelId = channelId;
        this.slackToken = slackToken;
    }


    /**
     * Getter for Review File.
     *
     * @return
     */
    public String getReviewFile() {
        return reviewFile;
    }

    /**
     * Getter for Qa File.
     *
     * @return
     */
    public String getQaFile() {
        return qaFile;
    }

    /**
     * Getter for Channel ID.
     *
     * @return
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Getter for Slack Token.
     *
     * @return
     */
    public String getSlackToken() {
        return slackToken;
    }
}
