package me.labconnect.webapp.livesession;

import java.util.Comparator;

import me.labconnect.webapp.models.Assignment;
import me.labconnect.webapp.models.Attempt;
import me.labconnect.webapp.models.Student;
import me.labconnect.webapp.models.Submission;

/**
 * Compare two students based on their attempt count for a specific assignment
 * 
 * @see Attempt
 * @see LiveSession
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class AttemptAmountComparator implements Comparator<Student> {
    private Assignment assignment;

    /**
     * Create a new attempt count comparator for a specific assignment
     * 
     * @param assignment The assignment to compare the attempts for
     */
    public AttemptAmountComparator(Assignment assignment) {
        this.assignment = assignment;
    }

    /**
     * Compare two students based on their attempt count for a specific assignment
     * 
     * @param o1 The left-hand side of the comparison
     * @param o2 The right-hand side of the comparison
     * 
     * @return A negative integer if the left-hand student has less attempts than
     *         the right, {@code 0} if they have equal attempts, and a positive
     *         integer if the right-hand student has less attempts than the left one
     */
    @Override
    public int compare(Student o1, Student o2) {
        Submission firstSubmission;
        Submission secondSubmission;

        firstSubmission = o1.getSubmissionFor(assignment);
        secondSubmission = o2.getSubmissionFor(assignment);

        return firstSubmission.getAttemptCount() - secondSubmission.getAttemptCount();
    }

}
