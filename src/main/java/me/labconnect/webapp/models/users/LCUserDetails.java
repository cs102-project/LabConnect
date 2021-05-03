package me.labconnect.webapp.models.users;

import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class representing LabConnect user's details
 *
 * @author Berk Çakar
 * @author Vedat Eren Arican
 * @version 01.05.2021
 */
public class LCUserDetails implements UserDetails {

    private LCUserRoleTypes role;
    private String password;
    private String username;
    private ObjectId id;
    private Collection<GrantedAuthority> authorities;

    /**
     * Default constructor for LCUserDetails takes an specified user object as a parameter
     *
     * @param user The user object
     */
    public LCUserDetails(User user) {
        this.role = user.getRoleType();
        this.password = user.getPassword();
        this.username = user.getEmail();
        this.id = user.getId();
        this.authorities = new ArrayList<>();
    }

    /**
     * Gets the object ID of the LabConnect user
     *
     * @return Object ID of the LabConnect user
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the granted authorities of the LabConnect user
     *
     * @return Granted authorities of the LabConnect user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

    /**
     * Gets the password of the LabConnect user (crypted)
     *
     * @return Password of the LabConnect user (crypted)
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Gets the username of the LabConnect user
     *
     * @return Username of the LabConnect user
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Checks whether the LabConnect user account is expired or not
     *
     * @return {@code true} if the LabConnect user account is expired, {@code false} otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks whether the LabConnect user account is non-locked or not
     *
     * @return {@code true} if the LabConnect user account is non-locked, {@code false} otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks whether the LabConnect user account's credentials is non-expired or not
     *
     * @return {@code true} if the LabConnect user account's credentials is non-expired, {@code false} otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks whether the LabConnect user account is enabled or not
     *
     * @return {@code true} if the LabConnect user account is enabled, {@code false} otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
