package me.labconnect.webapp.controller;

import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.InstructorService;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.models.users.services.UserService;
import me.labconnect.webapp.repository.UserRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * REST controller for handling {@code HTTP} requests for instructor specific functions
 *
 * @author Vedat Eren Arıcan
 * @author Berkan Şahin
 * @author Alp Ertan
 * @version 02.05.2021
 */
public class InstructorController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private InstructorService instructorService;

    /**
     * Adds/{@code POST} announcement
     *
     * @param authentication Token for authentication request
     * @param announcement   The announcement which is going to be added
     */
    @PostMapping("/api/instructor/announcements")
    @Secured({ "ROLE_INSTRUCTOR" })
    @ResponseStatus(value=HttpStatus.OK)
    public void addAnnouncement(Authentication authentication,
                                @RequestParam(value = "announcementContent") String content) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        if (user.getRoleType() != LCUserRoleTypes.INSTRUCTOR) {
            return;
        }

        Instructor instructor = userService.getInstructorDocumentOf(user);

        instructorService.addAnnouncementFrom(instructor, new Announcement(content, new Date(), user.getName()));

    }

}
