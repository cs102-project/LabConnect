package me.labconnect.webapp.models.testing;

import me.labconnect.webapp.models.testing.style.StyleChecker;
import org.springframework.data.annotation.PersistenceConstructor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A model of a test result.
 * <p>
 * Includes unit test output as well as the submission, test state etc.
 *
 * @author Vedat Eren Arıcan
 * @author Berkan Şahin
 * @author Alp Ertan
 * @version 30.04.2021
 * @see Tester
 */
public class TestResult {

    private Tests testType;
    private String testName;
    private TestState state;
    private ArrayList<String> testOutput;
    private String submissionPath;

    /**
     * A constructor used for retrieving existing TestResult entries from a database
     *
     * @param testType       The type of the test this is a result of
     * @param testName       The name of the test this is a result of
     * @param state          The state of the test
     * @param testOutput     The compiler/runtime/diff output obtained from the test
     * @param submissionPath The filename of the submission tested
     */
    @PersistenceConstructor
    public TestResult(Tests testType, String testName, TestState state, ArrayList<String> testOutput,
                      String submissionPath) {
        this.testOutput = testOutput;
        this.testName = testName;
        this.testType = testType;
        this.state = state;
        this.submissionPath = submissionPath;
    }

    /**
     * Create a TestResult instance for a unit test
     *
     * @param unitTest   The unit test the instance is instantiated from
     * @param submission The submission used in the unit test, as an absolute path
     * @param output     The compiler or runtime output, depending on state
     * @param state      The test state
     * @throws IOException If an I/O error occurs while reading the output
     * @apiNote If an error occurred during the compilation phase, use {@link
     * TestResult#TestResult(UnitTest, Path, CompilationException)}
     */
    public TestResult(UnitTest unitTest, Path submission, Path output, TestState state)
            throws IOException {
        this.testName = unitTest.getName();
        this.testType = unitTest.getTestType();
        this.submissionPath = submission.toString();
        this.state = state;

        Scanner scan = new Scanner(output);

        testOutput = new ArrayList<>();
        while (scan.hasNextLine()) {
            testOutput.add(scan.nextLine());
        }

        scan.close();
    }

    /**
     * Create a TestResult instance for a unit test
     *
     * @param unitTest       The unit test the instance is instantiated from
     * @param submission     The submission used in the unit test, as an absolute path
     * @param offendingLines The lines that are different from the example implementation
     * @param state          The test state
     * @apiNote If an error occurred during the compilation phase, use {@link
     * TestResult#TestResult(UnitTest, Path, CompilationException)}
     */
    public TestResult(UnitTest unitTest, Path submission, ArrayList<String> offendingLines,
                      TestState state) {
        this.testName = unitTest.getName();
        this.testType = unitTest.getTestType();
        this.submissionPath = submission.toString();
        this.state = state;
        this.testOutput = offendingLines;
    }

    /**
     * Creates a TestResult instance for a style check
     *
     * @param styleCheck     The style check the constructor is called from
     * @param submission     The submission used in the style check
     * @param offendingLines The lines that fail the style check
     */
    public TestResult(StyleChecker styleCheck, Path submission, ArrayList<String> offendingLines) {
        this.testName = styleCheck.getName();
        this.testType = styleCheck.getTestType();
        this.submissionPath = submission.toString();
        this.testOutput = offendingLines;

        state = offendingLines.isEmpty() ? TestState.SUCCESS : TestState.DESIGN_ERROR;

    }

    /**
     * Create a TestResult instance in case of a compiler error
     *
     * @param unitTest   The unit test the error occurred in
     * @param submission The submission causing the error
     * @param exception  The CompilationException instance
     */
    public TestResult(UnitTest unitTest, Path submission, CompilationException exception) {
        this.testType = unitTest.getTestType();
        this.testName = unitTest.getName();
        this.submissionPath = submission.toString();
        state = TestState.COMPILER_ERROR;

        testOutput = exception.getCompilerOutput();
    }

    /**
     * Return true if the test was successful
     *
     * @return true if the test is successful, otherwise false
     */
    public boolean isSuccessful() {
        return state == TestState.SUCCESS;
    }

    /**
     * Return the test state
     *
     * @return the test state
     */
    public TestState getState() {
        return state;
    }

    /**
     * Return the compiler or runtime output, depending on the state
     *
     * @return The compiler output in case of a compile-time error, otherwise the runtime output
     */
    public ArrayList<String> getOutput() {
        return testOutput;
    }

    /**
     * Return the path of the submission
     *
     * @return the path of the submitted source code
     */
    public Path getSubmission() {
        return Paths.get(submissionPath);
    }

    /**
     * Return the test this object is the result of
     *
     * @return the test for this result
     */
    public Tests getTestType() {
        return testType;
    }

    /**
     * Return the name of the test this is the result of
     *
     * @return The name of the test this is the result of
     */
    public String getTestName() {
        return testName;
    }
}
