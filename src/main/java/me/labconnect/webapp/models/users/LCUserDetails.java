package me.labconnect.webapp.models.users;

import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class LCUserDetails implements UserDetails {

    private LCUserRoleTypes role;
    private String password;
    private String username;
    private ObjectId id;
    private Collection<GrantedAuthority> authorities;

    public LCUserDetails(User user) {
        this.role = user.getRoleType();
        this.password = user.getPassword();
        this.username = user.getEmail();
        this.id = user.getId();
        this.authorities = new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
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
