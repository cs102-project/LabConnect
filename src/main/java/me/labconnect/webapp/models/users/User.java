package me.labconnect.webapp.models.users;

import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import me.labconnect.webapp.models.data.Course;

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
    
    protected String institution;
    protected String institutionId;
    protected List<Course> courses;
    
    protected String name;
    
    protected String email;
    protected String password;
    protected Collection<? extends GrantedAuthority> authorities; // There needs to be some indicator as to what level of authorization this user has.

    // Constructor
    /**
     * A constructor for retrieving User entries from the database
     * 
     * @param id      The unique objectID assigned by the database
     * @param institutionId The unique ID number assigned by the institution
     * @param name          The name of the user
     * @param email    The department of the user
     * @param isOnline      The online status of the user
     */
    public User(ObjectId roleDocumentId, String institution, String institutionId, List<Course> courses, String name, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.roleDocumentId = roleDocumentId;
        this.name = name;
        this.institution = institution;
        this.institutionId = institutionId;
        this.courses = courses;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Methods
    
    public ObjectId getId() {
        return id;
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public ObjectId getRoleDocumentId() {
        return roleDocumentId;
    }
    
    public String getEmail() {
        return email;
    }
    
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
    public String getDepartment() {
        return institution;
    }

    /**
     * Check if the given user is this user
     * 
     * @param other The object to check for equality
     * @return {@code true} if the institution IDs are equal, otherwise
     *         {@code false}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            User tmp;
            tmp = (User) other;
            return institutionId == tmp.institutionId;
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
