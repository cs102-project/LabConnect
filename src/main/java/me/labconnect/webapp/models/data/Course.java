package me.labconnect.webapp.models.data;

import java.util.Objects;

/**
 * Model class for {@code Course} objects
 *
 * @author Vedat Eren Arican
 * @author Berk Çakar
 * @version 03.05.2021
 */
public class Course {

    private String course;
    private int section;

    /**
     * Default constructor for the {@code Course} class takes name of the course and section number as
     * parameters
     *
     * @param course  The name of the course
     * @param section The section number of the course
     */
    public Course(String course, int section) {
        this.course = course;
        this.section = section;
    }

    /**
     * Gets the name of the {@code Course} object
     *
     * @return The name of the {@code Course} object
     */
    public String getCourse() {
        return course;
    }

    /**
     * Gets the section number of the {@code Course} object
     *
     * @return The section number of the {@code Course} object
     */
    public int getSection() {
        return section;
    }

    /**
     * Checks whether two {@code Course} objects are the same or not
     *
     * @param o The {@code Course} object to compare with
     * @return {code true} if two {@code Course} objects are the same, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course1 = (Course) o;
        return section == course1.section && Objects.equals(course, course1.course);
    }

    /**
     * Gets the hash code of the {@code Course} object
     *
     * @return The hash code of the {@code Course} object
     */
    @Override
    public int hashCode() {
        return Objects.hash(course, section);
    }
}
