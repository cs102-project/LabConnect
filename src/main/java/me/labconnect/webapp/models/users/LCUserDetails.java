package me.labconnect.webapp.models.users;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LCUserDetails implements UserDetails {
    
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;
    private ObjectId id;
    
    public LCUserDetails(User user) {
        this.authorities = user.getAuthorities();
        this.password = user.getPassword();
        this.username = user.getEmail();
        this.id = user.getId();
    }

    public ObjectId getId() {
        return id;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
