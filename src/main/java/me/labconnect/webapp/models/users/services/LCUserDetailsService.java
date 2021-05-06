package me.labconnect.webapp.models.users.services;

import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Serves different LabConnect user details functions for controllers
 *
 * @author Vedat Eren Arican
 * @version 03.05.2021
 */
public class LCUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads an user by using its username
     *
     * @param username Username of the user
     * @return LabConnect user details of the user whose username is specified as a parameter
     */
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
