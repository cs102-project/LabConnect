package me.labconnect.webapp.testing;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Tester interface for classes that are testing
 * distinct elements of an submission
 * @author Borga Haktan Bilen
 * @version 21/04/2021
 * @throws IOException If an I/O error occurs while processing the submission
 */
public interface Tester {
    /**
     * Runs distinct test for testing discrete elements
     * of an submission
     * @param submission The path for the submission file
     * @return TestResult object
     */
    TestResult runTest( Path submission ) throws IOException;
}
