package me.labconnect.webapp.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

/**
 * An aggregation of attempts for a certain assignment
 * 
 * @author Berkan Şahin
 * @version 22.04.2021
 */
public class Submission {
    
    // Variables
    @Id public String ID;
    private Student submitter;
    private Assignment assignment;
    private ArrayList<Attempt> attempts;

    // Constructors

    /**
     * Create a new submission
     * @param submitter The student the submission belongs to
     * @param assignment The assignment this submission is for
     */
    public Submission(Student submitter, Assignment assignment) {
        this.submitter = submitter;
        this.assignment = assignment;
        attempts = new ArrayList<>();
    }

    // Methods

    /**
     * Returns the student this submission belongs to
     * @return the student this submission belongs to
     */
    public Student getStudent() {
        return submitter;
    }

    /**
     * Returns the assignment this submission is for
     * @return the assignment this submission is for
     */
    public Assignment getAssignment() {
        return assignment;
    }

    /**
     * Add an attempt to this submission
     * @param attempt The attempt to add
     */
    public void addAttempt(Attempt attempt) {
        attempts.add(attempt);
    }

    /**
     * Returns the list of attempts 
     * @return the list of attempts
     */
    public ArrayList<Attempt> getAttempts() {
        return attempts;
    }

    /**
     * Returns the grade for the final attempt
     * @return the grade for the final attempt
     */
    public int getFinalGrade() {
        if (attempts.size() > 0) {
            return attempts.get(attempts.size() - 1).getGrade();
        } else {
            return 0;
        }
    }
}
