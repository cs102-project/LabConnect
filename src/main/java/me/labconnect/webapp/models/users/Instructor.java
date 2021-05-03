package me.labconnect.webapp.models.users;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.data.Announcement;

/**
 * An instructor, which is the user that creates assignments, starts live
 * sessions and uploads tests
 * 
 * @author Berkan Şahin
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
    
    public Instructor(List<Announcement> announcements, List<ObjectId> assignments) {
        this.announcements = announcements;
        this.assignments = assignments;
    }

    // Methods

    public ObjectId getId() {
        return id;
    }
    
    public List<Announcement> getAnnouncements() {
        return announcements;
    }
    
    public List<ObjectId> getAssignments() {
        return assignments;
    }
    
}
