package me.labconnect.webapp.models;

import java.util.ArrayList;


/**
 * An aggregation of attempts for a certain assignment
 * 
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class Submission {
    
    // Variables
    private long submitterID;
    private Assignment assignment;
    private ArrayList<Attempt> attempts;

    // Constructors
    
    /**
     * Create a new submission
     * @param submitter The student the submission belongs to
     * @param assignment The assignment this submission is for
     */
    public Submission(Student submitter, Assignment assignment) {
        this.submitterID = submitter.getUserId();
        this.assignment = assignment;
        attempts = new ArrayList<>();
    }

    // Methods

    /**
     * Returns the ID of the student this submission belongs to
     * @return the student this submission belongs to
     */
    public long getSubmitterID() {
        return submitterID;
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

    /**
     * Get the final attempt for this submission
     * @return the final attempt for this submission if it exists
     */
    public Attempt getFinalAttempt() {
        if (attempts.size() > 0) {
            return attempts.get(attempts.size() - 1);
        } else {
            return null;
        }
    }
    /**
     * Returns the amount of attempts for this submission
     * @return the amount of attempts for this submission
     */
    public int getAttemptCount() {
        return attempts.size();
    }
}
