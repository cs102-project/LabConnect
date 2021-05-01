package me.labconnect.webapp.models.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import me.labconnect.webapp.models.testing.TestResult;
import me.labconnect.webapp.models.testing.Tester;

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
    private ObjectId objectID;
    private String attemptDir;
    private List<TestResult> testResults;
    private List<String> feedback;
    private int grade;

    // Constructors

    @PersistenceConstructor
    public Attempt(String attemptDir, int grade, List<String> feedback, List<TestResult> testResults, ObjectId objectID) {
        this.attemptDir = attemptDir;
        this.feedback = feedback;
        this.grade = grade;
        this.testResults = testResults;
        this.objectID = objectID;
    }

    /**
     * Extract and test the supplied attempt
     * 
     * @param attemptPath The path of the source code directory
     * @param assignment  The assignment this attempt is for
     * @throws IOException If processing the archive fails
     */
    public Attempt(Path attemptPath, Assignment assignment) throws IOException {
        attemptDir = attemptPath.toString();
        feedback = new ArrayList<>();
        testResults = runTests(assignment);
    }

    // Methods

    /**
     * Run all the tests for the assignment this attempt belongs to
     * 
     * @param assignment The assignment to retrieve the tests from
     * @return The list of results
     * @throws IOException If processing the submission directory fails
     */
    private List<TestResult> runTests(Assignment assignment) throws IOException {
        ArrayList<TestResult> results;

        results = new ArrayList<>();

        for (Tester test : assignment.getTests()) {
            results.add(test.runTest(getAttemptPath()));
        }

        testResults = results;

        return getTestResults();
    }

    /**
     * Returns the test results
     * 
     * @return the test results
     */
    public List<TestResult> getTestResults() {
        return testResults;
    }

    /**
     * Return the result for the specified test case
     * 
     * @param test The unit or style test
     * @return The test result if it is found, otherwise {@code null}
     */
    public TestResult getResultFor(Tester test) {

        return testResults.stream().filter(result -> result.getTest().equals(test)).findAny().orElseGet(() -> null);

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
     * Check if the current attempt passed all tests
     * 
     * @return {@code true} if all tests were successful, otherwise {@code false}
     */
    public boolean passedAllTests() {

        return testResults.stream().map(result -> !result.isSuccessful()).anyMatch(success -> success);

    }

    /**
     * Give feedback for this attempt
     * 
     * @param feedback The feedback as a list of lines
     */
    public void giveFeedback(List<String> feedback) {
        this.feedback = feedback;
    }

    /**
     * Returns the feedback for this attempt
     * 
     * @return the feedback for this attempt
     */
    public List<String> getFeedback() {
        return feedback;
    }

    /**
     * Get the directory this attempt is stored in
     * 
     * @return the directory this attempt is stored in
     */
    public Path getAttemptPath() {
        return Paths.get(attemptDir);
    }

    /**
     * Return the unique object identifier
     * 
     * @return the unique object identifier
     */
    public ObjectId getID() {
        return objectID;
    }

    @Override
    public boolean equals(Object obj) {
        Attempt tmp;

        if (obj instanceof Attempt) {
            tmp = (Attempt) obj;
            return objectID.equals(tmp.getID());
        }

        return false;
    }

}