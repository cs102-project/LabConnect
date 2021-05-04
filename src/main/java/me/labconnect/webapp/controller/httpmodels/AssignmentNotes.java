package me.labconnect.webapp.controller.httpmodels;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * An aggregation of {@link AttemptNote} objects for a particular assignment
 *
 * @author Vedat Eren Arican
 * @author Berk Çakar
 * @author Alp Ertan
 * @version 03.05.2021
 */
public class AssignmentNotes {

    private String assignmentTitle;
    private ObjectId assignmentId;
    private List<AttemptNote> attemptNotes;

    /**
     * Default constructor for the {@code AssignmentNotes} class
     *
     * @param assignmentId    The unique identifier of the assignment these notes are for
     * @param assignmentTitle The title of the assignment these notes are for
     * @param attemptNotes    The list of individual attempt notes
     */
    public AssignmentNotes(String assignmentTitle, ObjectId assignmentId,
                           List<AttemptNote> attemptNotes) {
        this.assignmentTitle = assignmentTitle;
        this.assignmentId = assignmentId;
        this.attemptNotes = attemptNotes;
    }

    /**
     * Get the title of the assignment these notes are for
     *
     * @return The title of the assignment
     */
    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    /**
     * Get the list of notes for individual attempts
     *
     * @return The list of attempt notes
     */
    public List<AttemptNote> getAttemptNotes() {
        return attemptNotes;
    }

    /**
     * Returns the unique object ID of the assignment these notes are for
     *
     * @return The unique object ID of the assignment these notes are for
     */
    public ObjectId getAssignmentId() {
        return assignmentId;
    }

    /**
     * A model of a note for a singular {@link me.labconnect.webapp.models.data.Attempt}
     *
     * @author Vedat Eren Arıcan
     * @version 04.05.2021
     */
    public static class AttemptNote {

        private String note;
        private int attempt;

        /**
         * Create a new AttemptNote instance
         *
         * @param note    The notes
         * @param attempt The number of the attempt
         */
        public AttemptNote(String note, int attempt) {
            this.note = note;
            this.attempt = attempt;
        }

        /**
         * Get the content of this attempt note
         *
         * @return The content of this attempt note
         */
        public String getNote() {
            return note;
        }

        /**
         * Get the number of the attempt this note is for
         *
         * @return The sequentially generated number of the attempt this note is for
         */
        public int getAttempt() {
            return attempt;
        }

    }

}
