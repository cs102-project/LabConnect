package me.labconnect.webapp.models.testing;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Tester interface for classes that are testing distinct elements of an
 * submission
 * 
 * @author Borga Haktan Bilen
 * @version 21.04.2021
 * @throws IOException If an I/O error occurs while processing the submission
 */
public interface Tester {
    /**
     * Runs distinct test for testing discrete elements of an submission
     * 
     * @param submission The <b>absolute</b> path of the submission directory
     *                   (usually src/)
     * @return TestResult object
     */
    public TestResult runTest(Path submission) throws IOException;

    /**
     * Returns the name of the test
     * 
     * @return the name of the test
     */
    public String getName();
}
