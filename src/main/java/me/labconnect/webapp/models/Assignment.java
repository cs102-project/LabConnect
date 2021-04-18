package me.labconnect.webapp.models;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;

import me.labconnect.webapp.unittest.UnitTest;

/**
 * A generic assignment model class
 * @author Berkan Şahin
 * @version 18.04.2021
 */
public class Assignment {

    // Constants
    final int MAX_GRADE = 100;
    final int MAX_ATTEMPTS = 5; // Should we really hard-code these? -Berkan

    // Variables
    @Id
    public Long id; // handled by the Mongo backend, do not modify by hand!

    int[] sections;
    String title;
    Date dueDate;
    ArrayList<UnitTest> unitTests;

    // TODO the rest

}
