package me.labconnect.webapp.controller.httpmodels;

/**
 * Representation of the feedback concept
 *
 * @author Vedat Eren Arıcan
 * @author Borga Haktan Bilen
 */
public class NewFeedback {

    private int grade;
    private String content;

    /**
     * Default constructor for the {@code Feedback} class
     *
     * @param grade   The grade of the attached (to feedback) work
     * @param content Content of the feedback
     */
    public NewFeedback(int grade, String content) {
        this.grade = grade;
        this.content = content;
    }

    /**
     * Gets the content of the feedback
     *
     * @return The feedback of the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the grade of the feedback
     *
     * @return The grade of the feedback
     */
    public int getGrade() {
        return grade;
    }

}
