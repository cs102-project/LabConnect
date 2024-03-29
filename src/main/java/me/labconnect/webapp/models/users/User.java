package me.labconnect.webapp.models.users;

import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A generic User of LabConnect
 *
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @author Vedat Eren Arican
 * @version 30.04.2021
 */
@Document(collection = "users")
public class User {

    // Properties
    @Id
    protected ObjectId id;
    protected ObjectId roleDocumentId;
    protected LCUserRoleTypes roleType;

    protected String institution;
    protected String institutionId;
    protected List<Course> courses;

    protected String name;

    protected String email;
    @JsonIgnore
    protected String password;

    // Constructor

    /**
     * A constructor for retrieving User entries from the database
     *
     * @param roleDocumentId The unique objectID assigned by the database
     * @param roleType       The role type for this user (student, instructor etc.)
     * @param institution    The institution this user belongs to
     * @param institutionId  The unique ID number assigned by the institution
     * @param courses        The list of courses this user is associated with
     * @param name           The name of the user
     * @param email          The department of the user
     * @param password       The <b>encrypted</b> password of the user
     */
    public User(ObjectId roleDocumentId, LCUserRoleTypes roleType, String institution,
                String institutionId, List<Course> courses,
                String name, String email, String password) {
        this.roleDocumentId = roleDocumentId;
        this.roleType = roleType;
        this.name = name;
        this.institution = institution;
        this.institutionId = institutionId;
        this.courses = courses;
        this.email = email;
        this.password = password;
    }

    // Methods

    /**
     * Gets the object ID of the user
     *
     * @return Object ID of the user
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the role type of the user
     *
     * @return Role type of the user
     */
    public LCUserRoleTypes getRoleType() {
        return roleType;
    }

    /**
     * Gets the role document ID of the user
     *
     * @return Role document ID of the user
     */
    public ObjectId getRoleDocumentId() {
        return roleDocumentId;
    }

    /**
     * Gets the e-mail of the user
     *
     * @return E-mail of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the encrypted password of the user
     *
     * @return Encrypted password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user id
     *
     * @return The user id as long type
     */
    public String getInstitutionId() {
        return institutionId;
    }

    /**
     * Gets the name of the user
     *
     * @return Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the department of the user.
     *
     * @return Department of the user.
     */
    public String getInstitution() {
        return institution;
    }

    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Check if the given user is this user
     *
     * @param other The object to check for equality
     * @return {@code true} if the institution IDs are equal, otherwise {@code false}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            User tmp;
            tmp = (User) other;
            return institutionId.equals(tmp.institutionId);
        }

        return false;
    }

    /**
     * Returns the hash code of the institution ID, for usage in hashmaps
     *
     * @return The hash code of the institution ID
     */
    @Override
    public int hashCode() {
        return institutionId.hashCode();
    }

}
