package me.labconnect.webapp.models.data;

import java.util.Date;

/**
 * Model class for {@code Announcement} objects
 *
 * @author Vedat Eren Arican
 * @author Berk Çakar
 * @version 03.05.2021
 */
public class Announcement {

    private String content;
    private Date date;

    /**
     * Default constructor for the {@code Announcement} class takes content of the note and date as parameters
     *
     * @param content String content of the {@code Announcement} object
     * @param date Date of the {@code Announcement} object
     */
    public Announcement(String content, Date date) {
        this.content = content;
        this.date = date;
    }

    /**
     * Gets the content of the {@code Announcement} object
     *
     * @return String content of the {@code Announcement} object
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the date of the {@code Announcement} object
     *
     * @return Date of the {@code Announcement} object
     */
    public Date getDate() {
        return date;
    }

}
