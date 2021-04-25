package me.labconnect.webapp.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class representing a student, i.e., a User that submits solutions to
 * assignments and waits in queues
 * 
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @version 23.04.2021
 */
public class Student extends User {

    // Properties
    ArrayList<Assignment> assignments;
    HashMap<Assignment, Submission> submissions;

    // Constructor

    /**
     * Create a new Student with pre-determined assignments.
     * 
     * @param name          Name of the student.
     * @param institutionId Unique institution id of the student.
     * @param department    Student's department.
     * @param assignments   Assignments this student is responsible for
     */
    public Student(String name, long institutionId, String department, ArrayList<Assignment> assignments) {
        this(name, institutionId, department);
        this.assignments = assignments;
    }

    /**
     * Create a new student instance with no assigned assignments
     * 
     * @param name          The name of the student
     * @param institutionId Unique institution ID of the student.
     * @param department    The student's department
     */
    public Student(String name, long institutionId, String department) {
        super(name, institutionId, department);
        assignments = new ArrayList<>();
        submissions = new HashMap<>();
    }

    // Methods
    /**
     * Adds an assignment object to the assignments list.
     * 
     * @param assignment The assignment which is going to be added.
     */
    public void giveAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    /**
     * Gets the assignments list of a specific Student.
     * 
     * @return ArrayList of assigned Assignments.
     */
    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * Gets the assignments from the list in the specified index.
     * 
     * @param index The Index which contains the desired assignment.
     * @return The required assignment.
     */
    public Assignment getAssignmentByIndex(int index) {

        if (assignments.size() >= index && index < 0) {
            return null;
        } else {
            return assignments.get(index);
        }
    }

    /**
     * Add a submission for an assignment
     * 
     * @param assignment The assignment the submission is for
     * @param submission The submission to add
     * @return {@code true} if the assignment is assigned to the student, otherwise
     *         {@code false}
     */
    public boolean addSubmission(Assignment assignment, Submission submission) {
        if (!assignments.contains(assignment)) {
            return false;
        } else {
            submissions.put(assignment, submission);
            return true;
        }

    }

    /**
     * Return a mapping of submissions to assignments
     * 
     * @return a mapping of submissions to assignments
     */
    public HashMap<Assignment, Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Return a submission for a specific assignment
     * 
     * @param assignment The assignment the submission belongs to
     * @return The submission if it's found, otherwise {@code null}
     */
    public Submission getSubmissionFor(Assignment assignment) {
        return submissions.get(assignment);
    }

}
