package me.labconnect.webapp.models.testing;

/**
 * An enumeration of possible states for a UnitTest
 *
 * @author Berkan Şahin
 * @version 21.04.2021
 * @see me.labconnect.webapp.models.testing.Tester
 * @see TestResult
 */
public enum TestState {
    COMPILER_ERROR,
    TIMEOUT,
    SUCCESS,
    OUTPUT_MISMATCH,
    RUNTIME_ERROR,
    DESIGN_ERROR
}
