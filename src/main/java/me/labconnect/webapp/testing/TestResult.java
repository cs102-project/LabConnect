package me.labconnect.webapp.testing;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import me.labconnect.webapp.testing.style.StyleChecker;

/**
 * A model of a test result.
 * 
 * Includes unit test output as well as the submission, test state etc.
 * 
 * @see Tester
 * @author Berkan Şahin
 * @version 21.04.2021
 */
public class TestResult {

    private Tester test;
    private TestState state;
    private ArrayList<String> testOutput;
    private Path submission;

    /**
     * Create a TestResult instance for a unit test
     * 
     * @apiNote If an error occured during the compilation phase, use
     *          {@link TestResult#TestResult(UnitTest, Path, CompilationException)}
     * @param unitTest  The unit test the instance is instantiated from
     * @param submisson The submission used in the unit test
     * @param output    The compiler or runtime output, depending on state
     * @param state     The test state
     * 
     * @throws IOException If an I/O error occurs while reading the output
     */
    public TestResult(UnitTest unitTest, Path submisson, Path output, TestState state) throws IOException {
        this.test = unitTest;
        this.submission = submisson;
        this.state = state;

        Scanner scan = new Scanner(output);

        while (scan.hasNextLine())
            testOutput.add(scan.nextLine());

        scan.close();
    }

    /**
     * Creates a TestResult instance for a style check
     * 
     * 
     * @param styleCheck     The style check the constructor is called from
     * @param submission     The submission used in the style check
     * @param offendingLines The lines that fail the style check
     */
    public TestResult(StyleChecker styleCheck, Path submission, ArrayList<String> offendingLines) {
        this.test = styleCheck;
        this.submission = submission;
        this.testOutput = offendingLines;
        if (offendingLines.isEmpty()) {
            state = TestState.SUCCESS;
        } else {
            state = TestState.DESIGN_ERROR;
        }
    }

    /**
     * Create a TestResult instance in case of a compiler error
     * 
     * @param unitTest   The unit test the error occured in
     * @param submission The submission causing the error
     * @param exception  The CompilationException instance
     */
    public TestResult(UnitTest unitTest, Path submission, CompilationException exception) {
        this.test = unitTest;
        this.submission = submission;
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
     * @return The compiler output in case of a compile-time error, otherwise the
     *         runtime output
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
        return submission;
    }

    /**
     * Return the test this object is the result of
     * 
     * @return the test for this result
     */
    public Tester getTest() {
        return test;
    }
}
