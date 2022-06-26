package CS601.project3;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains the Q&A file object,
 * taken from Project 1.
 *
 * @author Aneesh Madhavan.
 */
public class Qa extends Data {

    private String questionType;
    private String answerTime;
    private long unixTime;
    private String question;
    private String answerType;
    private String answer;

    /**
     * Constructor for class.
     *
     * @param questionType
     * @param asin
     * @param answerTime
     * @param unixTime
     * @param question
     * @param answerType
     * @param answer
     */
    public Qa(String questionType, String asin, String answerTime, long unixTime, String question,
              String answerType, String answer) {
        this.questionType = questionType;
        this.asin = asin;
        this.answerTime = answerTime;
        this.unixTime = unixTime;
        this.question = question;
        this.answerType = answerType;
        this.answer = answer;
    }

    /**
     * Default constructor for class.
     */
    public Qa() {

    }

    /**
     * Getter for questionType.
     *
     * @return
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * Getter for answerTime.
     *
     * @return
     */
    public String getAnswerTime() {
        return answerTime;
    }

    /**
     * Getter for unixTime.
     *
     * @return
     */
    public long getUnixTime() {
        return unixTime;
    }

    /**
     * Getter for getQuestion.
     *
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Getter for answerType.
     *
     * @return
     */
    public String getAnswerType() {
        return answerType;
    }

    /**
     * Getter for answer.
     *
     * @return
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * toString method for the object.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Qa{" +
                "asin='" + asin + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

}