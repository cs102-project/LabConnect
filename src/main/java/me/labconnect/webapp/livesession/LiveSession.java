package me.labconnect.webapp.livesession;

import java.util.ArrayList;

import me.labconnect.webapp.models.Assignment;
import me.labconnect.webapp.models.Student;

/**
 * A live session with a singular queue of students
 * 
 * @author Alp Ertan
 * @author Berkan Şahin
 * @version 27.04.2021
 */
public abstract class LiveSession {

    // Properties
    protected String sessionID;
    protected Assignment sessionLab;
    protected ArrayList<Student> studentQueue;

    // Constructors

    /**
     * Creates a new Live Session Manager with a determined assignment
     * 
     * @param sessionID  The ID of the session
     * @param sessionLab The assignment of the lab
     */
    public LiveSession(String sessionID, Assignment sessionLab) {
        this.sessionID = sessionID;
        this.sessionLab = sessionLab;
        this.studentQueue = new ArrayList<>();
    }

    // Methods

    /**
     * Adds a new student to the live session queue
     * <p>
     * Any custom ordering of the queue must be implemented in this method
     * 
     * @param newStudent The student being added to the session queue
     * @return {@code true} if the student can attend this session
     * 
     * @implNote The default implementation always returns {@code true}
     */
    public boolean addStudent(Student newStudent) {
        studentQueue.add(newStudent);
        return true;
    }

    /**
     * Retrieves, but <b>does not remove</b> the next student in the queue
     * 
     * @see LiveSession#proceedQueue()
     * @return next student in queue
     */
    public Student getNextStudent() {
        return studentQueue.get(0);
    }

    /**
     * Procceds the student queue by removing the leftmost student, i.e, the student
     * at the beginning of the underlying list
     * 
     * @return The student that was removed
     */
    public Student proceedQueue() {
        if (studentQueue.size() > 0) {
            return studentQueue.remove(0);
        }
        return null;
    }

    /**
     * Returns the amount of the students waiting for a live session
     * 
     * @return The number of students waiting in the queue
     */
    public int getWaitingStudentCount() {
        return studentQueue.size();
    }

    /**
     * Clears all students from the queue
     */
    public void endSession() {
        studentQueue.clear();
    }

    /**
     * Return the <b>ordered</b> list of the students currently in queue
     * <p>
     * The returned ArrayList is ordered from front to back
     * 
     * @return The student queue as a list
     */
    public ArrayList<Student> getStudentQueue() {
        return studentQueue;
    }

    /**
     * Returns the assignment for this live session
     * 
     * @return the assignment for this live session
     */
    public Assignment getSessionAssignment() {
        return sessionLab;
    }

}