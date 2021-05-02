package me.labconnect.webapp.models.data;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import me.labconnect.webapp.models.testing.TestResult;

/**
 * Model of a singular attempt, containing the submission path and results of
 * the tests
 * 
 * @author Berkan Şahin
 * @author Vedat Eren Arican
 * @version 01.05.2021
 */
public class Attempt {

    // Variables
    @Id
    private ObjectId id;
    private String attemptFilename;
    private String note;
    private List<TestResult> testResults;
    private String feedback;
    private int grade;

    // Constructors

    public Attempt(ObjectId id, String attemptFilename, String note, int grade, String feedback, List<TestResult> testResults) {
        this.id = id;
        this.attemptFilename = attemptFilename;
        this.note = note;
        this.grade = grade;
        this.feedback = feedback;
        this.testResults = testResults;
    }
    
    // Methods

    /**
     * Returns the test results
     * 
     * @return the test results
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
     * Set the grade for this attempt
     * 
     * @param grade The grade
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * Returns the grade for this attempt
     * 
     * @return The grade for this attempt
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Give feedback for this attempt
     * 
     * @param feedback The feedback as a list of lines
     */
    public void giveFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Returns the feedback for this attempt
     * 
     * @return the feedback for this attempt
     */
    public String getFeedback() {
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
    public ObjectId getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object obj) {
        Attempt tmp;

        if (obj instanceof Attempt) {
            tmp = (Attempt) obj;
            return id.equals(tmp.getId());
        }

        return false;
    }

}