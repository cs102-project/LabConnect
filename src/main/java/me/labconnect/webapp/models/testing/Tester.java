package me.labconnect.webapp.models.testing;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Tester interface for classes that are testing distinct elements of an submission
 *
 * @author Borga Haktan Bilen
 * @author Alp Ertan
 * @version 04.05.2021
 */
public interface Tester {

    /**
     * Runs distinct test for testing discrete elements of an submission
     *
     * @param submission The <b>absolute</b> path of the submission directory (usually src/)
     * @return TestResult object
     * @throws IOException If processing the attempt fails
     */
    TestResult runTest(Path submission) throws IOException;

    /**
     * Returns the name of the test
     *
     * @return the name of the test
     */
    String getName();

    /**
     * Return the type of this test as a {@link Tests} enum
     *
     * @see Tests
     * @return The type of this test
     */
    Tests getTestType();
}
