package me.labconnect.webapp.models.users;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.livesession.Meetable;
/**
 * Class representing a TA, i.e., a User that can grade Attempts, meet with
 * students and manage a meeting queue
 *
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @version 30.04.2021
 */
@Document(collection = "assistants")
public class TeachingAssistant implements Meetable {

    // Properties
    @Id
    //@JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String meetingLink;
    //@JsonSerialize(using = ToStringSerializer.class)
    private List<ObjectId> students;

    // Constructors

    /**
     * Initializes the Teaching assistant object
     *
     * @param meetingLink The link to the personal meeting room of the TA
     * @param students The Object IDs of the students assigned to the TAs
     */
    public TeachingAssistant(String meetingLink, List<ObjectId> students) {
        this.meetingLink = meetingLink;
        this.students = students;
    }

    // Methods

    /**
     * Gets the object ID of the teaching assistant
     *
     * @return Object ID of the teaching assistant
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the meeting link of the teaching assistant
     *
     * @return Meeting link of the teaching assistant
     */
    public String getMeetingLink() {
        return meetingLink;
    }

    /**
     * Gets the students which is assigned to this teaching assistant.
     *
     * @return ArrayList of assigned Student objects.
     */
    //@JsonSerialize(using = ToStringSerializer.class)
    public List<ObjectId> getStudents() {
        return students;
    }

}