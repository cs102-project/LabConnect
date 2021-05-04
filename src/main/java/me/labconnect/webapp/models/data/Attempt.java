package me.labconnect.webapp.models.data;

import me.labconnect.webapp.models.testing.TestResult;
import org.springframework.data.annotation.Id;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Model of a singular attempt, containing the submission path and results of the tests
 *
 * @author Berkan Şahin
 * @author Vedat Eren Arican
 * @version 01.05.2021
 */
public class Attempt {

    // Variables
    @Id
    //@JsonSerialize(using = ToStringSerializer.class)
    private int id;
    private String attemptFilename;
    private String note;
    private List<TestResult> testResults;
    private Feedback feedback;

    // Constructors

    public Attempt(int id, String attemptFilename, String note, Feedback feedback,
                   List<TestResult> testResults) {
        this.id = id;
        this.attemptFilename = attemptFilename;
        this.note = note;
        this.feedback = feedback;
        this.testResults = testResults;
    }

    // Methods

    /**
     * Gets the test results
     *
     * @return The test results
     */
    public List<TestResult> getTestResults() {
        return testResults;
    }

    /**
     * Set the test results
     *
     * @param testResults The test results
     */
    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    /**
     * Give feedback for this attempt
     *
     * @param feedback The feedback as a list of lines
     */
    public void giveFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    // TODO fix the javadocs for feedback methods

    /**
     * Gets the feedback for this attempt
     *
     * @return The feedback for this attempt
     */
    public Feedback getFeedback() {
        return feedback;
    }

    /**
     * Get the directory this attempt is stored in
     *
     * @return the directory this attempt is stored in
     */
    public Path getAttemptFilename() {
        return Paths.get(attemptFilename);
    }

    /**
     * Return the unique object identifier
     *
     * @return the unique object identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the string representation of the Note
     *
     * @return The string representation of the Note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the notes content with specified parameter
     *
     * @param note The content of the note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Checks whether two attempt objects are the same or not
     *
     * @param obj The attempt object to compare with
     * @return {code true} if two attempt objects are the same, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        Attempt tmp;

        if (obj instanceof Attempt) {
            tmp = (Attempt) obj;
            return id == tmp.getId();
        }

        return false;
    }

}