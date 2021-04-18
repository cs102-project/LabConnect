package me.labconnect.webapp.unittest;

/**
 * An enumeration of possible states for a UnitTest
 * 
 * @see UnitTest
 * @author Berkan Şahin
 * @version 18.04.2021
 */
public enum TestState {
    COMPILER_ERROR, TIMEOUT, SUCCESS, OUTPUT_MISMATCH, RUNTIME_ERROR
}
