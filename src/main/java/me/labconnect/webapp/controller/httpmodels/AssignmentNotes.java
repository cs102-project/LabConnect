package me.labconnect.webapp.controller.httpmodels;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * HTTP model class for {@code Note} objects
 *
 * @author Vedat Eren Arican
 * @author Berk Çakar
 * @version 03.05.2021
 */
public class AssignmentNotes {

    private String assignmentTitle;
    private ObjectId assignmentId;
    private List<AttemptNote> attemptNotes;

    /**
     * Default constructor for the {@code Note} class takes content of the note as parameter
     *
     * @param content String content of the {@code Note} object
     */
    public AssignmentNotes(String assignmentTitle, ObjectId assignmentId,
                           List<AttemptNote> attemptNotes) {
        this.assignmentTitle = assignmentTitle;
        this.assignmentId = assignmentId;
        this.attemptNotes = attemptNotes;
    }

    /**
     * Gets the content of the {@code Note} object
     *
     * @return String content of the {@code Note} object
     */
    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public List<AttemptNote> getAttemptNotes() {
        return attemptNotes;
    }

    public ObjectId getAssignmentId() {
        return assignmentId;
    }

    public static class AttemptNote {

        private String note;
        private int attempt;

        public AttemptNote(String note, int attempt) {
            this.note = note;
            this.attempt = attempt;
        }

        public String getNote() {
            return note;
        }

        public int getAttempt() {
            return attempt;
        }

    }

}
