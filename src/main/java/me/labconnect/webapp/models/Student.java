package me.labconnect.webapp.models;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A class representing a student, i.e., a User that submits solutions to
 * assignments and waits in queues
 * 
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @version 28.04.2021
 */
@Document(collection = "users")
@TypeAlias("student")
public class Student extends User {

    // Properties
    private ArrayList<Assignment> assignments;
    private HashMap<Assignment, Submission> submissions;
    private int section;

    // Constructor

    /**
     * Create a new Student with pre-determined assignments.
     * 
     * @param name          Name of the student.
     * @param institutionId Unique institution id of the student.
     * @param department    Student's department.
     * @param section       The section this student is in
     * @param assignments   Assignments this student is responsible for
     */
    public Student(String name, long institutionId, String department, int section, ArrayList<Assignment> assignments) {
        this(name, institutionId, department, section);
        this.assignments = assignments;
    }

    /**
     * Create a new student instance with no assigned assignments
     * 
     * @param name          The name of the student
     * @param institutionId Unique institution ID of the student.
     * @param department    The student's department
     * @param section       The section this student is in
     */
    public Student(String name, long institutionId, String department, int section) {
        super(name, institutionId, department);
        this.section = section;
        assignments = new ArrayList<>();
        submissions = new HashMap<>();
    }

    private Student() {}

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

    /**
     * Returns the section this student is in
     * 
     * @return the section this student is in
     */
    public int getSection() {
        return section;
    }
}
