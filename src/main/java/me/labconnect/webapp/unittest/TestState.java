package me.labconnect.webapp.unittest;

/**
 * An enumeration of possible states for a UnitTest
 * 
 * @see me.labconnect.webapp.models.Tester
 * @see TestResult
 * @author Berkan Şahin
 * @version 21.04.2021
 */
public enum TestState {
    COMPILER_ERROR, TIMEOUT, SUCCESS, OUTPUT_MISMATCH, RUNTIME_ERROR, DESIGN_ERROR
}
