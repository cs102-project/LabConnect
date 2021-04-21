package me.labconnect.webapp.models;

import java.io.IOException;
import java.nio.file.Path;

import me.labconnect.webapp.unittest.TestResult;

/**
 * Tester interface for classes that are testing
 * distinct elements of an submission
 * @author Borga Haktan Bilen
 * @version 21/04/2021
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
