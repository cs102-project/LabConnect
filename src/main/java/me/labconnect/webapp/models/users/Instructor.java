package me.labconnect.webapp.models.users;

import me.labconnect.webapp.models.data.Announcement;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * An instructor, which is the user that creates assignments, starts live sessions and uploads
 * tests
 *
 * @author Berkan Şahin
 * @author Berk Çakar
 * @version 27.04.2021
 */
@Document(collection = "instructors")
public class Instructor {

    // Variables
    @Id
    //@JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private List<Announcement> announcements;
    //@JsonSerialize(using = ToStringSerializer.class)
    private List<ObjectId> assignments;

    /**
     * Default constructor for Instructor takes lists of announcements and assignments as parameters
     *
     * @param announcements Announcements of the instructor
     * @param assignments   Assignments given by the instructor
     */
    public Instructor(List<Announcement> announcements, List<ObjectId> assignments) {
        this.announcements = announcements;
        this.assignments = assignments;
    }

    // Methods

    /**
     * Gets the object ID of the instructor
     *
     * @return Object ID of the instructor
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the announcements of the instructor
     *
     * @return Announcements of the instructor
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Gets the assignments given by the instructor
     *
     * @return Assignments given by the instructor
     */
    public List<ObjectId> getAssignments() {
        return assignments;
    }

}
