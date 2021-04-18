package me.labconnect.webapp.unittest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A model of a unit test result.
 * 
 * Includes unit test output as well as execution time, any compiler errors etc.
 * 
 * @see UnitTest
 * @author Berkan Şahin
 * @version 18.04.2021
 */
public class TestResult {

    private UnitTest unitTest;
    private TestState state;
    private ArrayList<String> testOutput;
    private Path submission;

    /**
     * Create a TestResult instance
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
        this.unitTest = unitTest;
        this.submission = submisson;
        this.state = state;

        Scanner scan = new Scanner(output);

        while (scan.hasNextLine())
            testOutput.add(scan.nextLine());

        scan.close();
    }

    /**
     * Create a TestResult instance in case of a compiler error
     * 
     * @param unitTest   The unit test the error occured in
     * @param submission The submission causing the error
     * @param exception  The CompilationException instance
     */
    public TestResult(UnitTest unitTest, Path submission, CompilationException exception) {
        this.unitTest = unitTest;
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
     * @return the path of the submitted source code
     */
    public Path getSubmission() {
        return submission;
    }

    /**
     * Return the unit test this object is the result of
     * @return the unit test for this result
     */
    public UnitTest getTest() {
        return unitTest;
    }
}
