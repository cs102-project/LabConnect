package me.labconnect.webapp.testing;

/**
 * An exception thrown when the example implementation supplied by the
 * instructor fails the unit test.
 * 
 * @author Alp Ertan
 * @author Berkan Şahin
 * @version 21.04.2021
 */
public class BadExampleException extends Exception {

    // Variables
    TestResult result;

    // Constructors

    /**
     * Create a new BadExampleException instance
     * 
     * @param result The result of the failed test
     */
    public BadExampleException(TestResult result) {
        this.result = result;
    }

    // Methods

    /**
     * Return the result of the failed test
     * 
     * @return the result of the failed test
     */
    public TestResult getResult() {
        return result;
    }
}
