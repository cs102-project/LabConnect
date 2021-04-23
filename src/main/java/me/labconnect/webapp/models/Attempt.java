package me.labconnect.webapp.models;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import me.labconnect.webapp.testing.TestResult;
import me.labconnect.webapp.testing.Tester;

/**
 * Model of a singular attempt, containing the submission path and results of
 * the tests
 * 
 * @author Berkan Şahin
 * @version 23.04.2021
 */
public class Attempt {

    // Constants
    private final String SUBMISSION_ROOT = "/var/labconnect/submissions";

    // Variables
    @Id
    public String attemptID;
    private ArrayList<TestResult> testResults;
    private ArrayList<String> feedback;
    private Path submissionDir;
    private Submission submission;
    private int grade;

    // Constructors

    /**
     * Extract and test the supplied attempt
     * 
     * @param submissionArchive A .zip archive containing the source code root
     *                          (src/)
     * @param submission        The submission this attempt belongs to
     * @throws IOException If processing the archive fails
     */
    public Attempt(Path submissionArchive, Submission submission) throws IOException {
        Path submissionParent;
        ArrayList<String> extractorArgs;
        ProcessBuilder extractorBuilder;
        Process extractor;

        if (!submissionArchive.isAbsolute() || !submissionArchive.endsWith(".zip")) {
            throw new IOException("Invalid archive");
        }

        this.submission = submission;

        // Create submission dir
        submissionParent = Files.createDirectories(Paths.get(SUBMISSION_ROOT, submission.ID));

        submissionDir = Files.createTempDirectory(submissionParent, "attempt");

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

        testResults = runTests();
    }

    // Methods

    /**
     * Run all the tests for the assignment this attempt belongs to
     * 
     * @return The list of results
     * @throws IOException If processing the submission directory fails
     */
    private ArrayList<TestResult> runTests() throws IOException {
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
    public ArrayList<TestResult> getTestResults() {
        return testResults;
    }

    /**
     * Return the result for the specified test case
     * 
     * @param test The unit or style test
     * @return The test result if it is found, otherwise {@code null}
     */
    public TestResult getResultFor(Tester test) {
        for (TestResult result : testResults) {
            if (result.getTest().equals(test)) {
                return result;
            }
        }

        return null;
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
        for (TestResult result : testResults) {
            if (!result.isSuccessful()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Give feedback for this attempt
     * 
     * @param feedback The feedback as a list of lines
     */
    public void giveFeedback(ArrayList<String> feedback) {
        this.feedback = feedback;
    }

    /**
     * Returns the feedback for this attempt
     * 
     * @return the feedback for this attempt
     */
    public ArrayList<String> getFeedback() {
        return feedback;
    }

}
