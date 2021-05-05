package me.labconnect.webapp.models.data;

import java.util.Date;
import java.util.Objects;

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
    private String author;

    /**
     * Default constructor for the {@code Announcement} class takes content of the note and date as
     * parameters
     *
     * @param content String content of the {@code Announcement} object
     * @param date    Date of the {@code Announcement} object
     * @param author  The author of the announcement
     */
    public Announcement(String content, Date date, String author) {
        this.content = content;
        this.date = date;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    /**
     * Checks if the given object is an equivalent Announcement
     *
     * @param o The object to compare
     * @return {@code true} if the objects are equal, otherwise {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return Objects.equals(content, that.content) && Objects.equals(date, that.date) && Objects
                .equals(author, that.author);
    }

    /**
     * Generate a unique hash code for each equivalent Announcement
     *
     * @return The generated hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(content, date, author);
    }
}
