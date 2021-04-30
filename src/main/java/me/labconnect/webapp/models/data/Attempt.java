package me.labconnect.webapp.models.data;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.testing.TestResult;
import me.labconnect.webapp.models.testing.Tester;

/**
 * Model of a singular attempt, containing the submission path and results of
 * the tests
 * 
 * @author Berkan Şahin
 * @author Vedat Eren Arican
 * @version 30.04.2021
 */
@Document(collection="attempts")
public class Attempt {

    // Constants
    @Transient
    private final String ATTEMPT_ROOT = "/var/labconnect/submissions";

    // Variables
    @Id
    private String objectID;
    private List<TestResult> testResults;
    private List<String> feedback;
    private String attemptID;
    private int grade;

    // Constructors

    @PersistenceConstructor
    public Attempt(String attemptID, int grade, List<String> feedback, List<TestResult> testResults, String objectID) {
        this.attemptID = attemptID;
        this.feedback = feedback;
        this.grade = grade;
        this.testResults = testResults;
        this.objectID = objectID;
    }
    /**
     * Extract and test the supplied attempt
     * 
     * @param submissionArchive A .zip archive containing the source code root
     *                          (src/)
     * @param submission        The submission this attempt belongs to
     * @throws IOException If processing the archive fails
     */
    public Attempt(Path submissionArchive, Submission submission) throws IOException {
        Path submissionDir;
        ArrayList<String> extractorArgs;
        ProcessBuilder extractorBuilder;
        Process extractor;

        if (!submissionArchive.isAbsolute() || !submissionArchive.getFileName().toString().endsWith(".zip")) {
            throw new IOException("Invalid archive");
        }

        // Create submission dir
        submissionDir = Files.createTempDirectory(Paths.get(ATTEMPT_ROOT), "");
        attemptID = submissionDir.getFileName().toString();

        // Unzip submission
        extractorArgs = new ArrayList<>();
        extractorArgs.add("unzip");

        // Rest of the args are derived from unzip manpage
        extractorArgs.add("-oqq"); // Overwrite existing files and suppress output

        extractorArgs.add(submissionArchive.toString());

        // Extract to submission dir
        extractorArgs.add("-d");
        extractorArgs.add(submissionDir.toString());

        extractorBuilder = new ProcessBuilder(extractorArgs);
        extractorBuilder.redirectOutput(Redirect.DISCARD);
        extractor = extractorBuilder.start();

        // Wait for extraction to end, then determine success from exit code
        try {
            if (extractor.waitFor() != 0) {
                throw new IOException("Extraction error");
            }
        } catch (InterruptedException e) {
            throw new IOException("unzip was interrupted");
        }

        testResults = runTests(submissionDir, submission);
    }

    // Methods

    /**
     * Run all the tests for the assignment this attempt belongs to
     * 
     * @return The list of results
     * @throws IOException If processing the submission directory fails
     */
    private ArrayList<TestResult> runTests(Path submissionDir, Submission submission) throws IOException {
        ArrayList<TestResult> results;

        results = new ArrayList<>();

        for (Tester test : submission.getAssignment().getTests()) {
            results.add(test.runTest(submissionDir));
        }

        return results;
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
    public Path getAttemptDir() {
        return Paths.get(ATTEMPT_ROOT, attemptID);
    }

    public String getAttemptID() {
        return attemptID;
    }

}
