package me.labconnect.webapp.models.users;

import me.labconnect.webapp.models.livesession.Meetable;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class representing a Tutor, i.e., a user that can meet with Students during a live session
 *
 * @author Borga Haktan Bilen
 * @author Vedat Eren Arican
 * @version 22.04.2021
 */
@Document(collection = "tutors")
public class Tutor implements Meetable {

    // Properties
    @Id
    //@JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String meetingLink;

    // Constructors

    /**
     * Initialize all properties of a Tutor object
     *
     * @param meetingLink The URL of the online meeting
     */
    public Tutor(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    /**
     * Gets the object ID of the tutor
     *
     * @return Object ID of the tutor
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the meeting link of the tutor
     *
     * @return Meeting link of the tutor
     */
    public String getMeetingLink() {
        return meetingLink;
    }

}
