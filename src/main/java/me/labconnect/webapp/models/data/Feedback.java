package me.labconnect.webapp.models.data;

import java.util.Date;

/**
 * Representation of the feedback concept
 *
 * @author Vedat Eren Arıcan
 * @author Borga Haktan Bilen
 */
public class Feedback {

    private int grade;
    private String content;
    private Date date;

    /**
     * Default constructor for the {@code Feedback} class
     *
     * @param grade   The grade of the attached (to feedback) work
     * @param content Content of the feedback
     * @param date    Date of the feedback
     */
    public Feedback(int grade, String content, Date date) {
        this.grade = grade;
        this.content = content;
        this.date = date;
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
     * Gets the date of the feedback
     *
     * @return The date of the feedback
     */
    public Date getDate() {
        return date;
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
