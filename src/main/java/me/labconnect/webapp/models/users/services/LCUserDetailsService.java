package me.labconnect.webapp.models.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.repository.UserRepository;

public class LCUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(username);
        
        if (user == null) {
            throw new UsernameNotFoundException(username + " was not found.");
        } else {
            return new LCUserDetails(user);
        }
        
    }
    
}
