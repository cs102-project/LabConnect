package me.labconnect.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.models.Instructor;
import me.labconnect.webapp.repository.InstructorRepository;

/**
 * A REST API that exposes instructor-related operations to clients
 * 
 * @author Berkan Şahin
 * @version 29.04.2021
 */
@RestController
public class InstructorAPI {

    @Autowired
    private InstructorRepository instructorRepository;

    /**
     * Add the given instructor entry to the database
     * 
     * @param newInstructor The new instructor entry in JSON format
     * @return The newly added instructor entry
     */
    @PostMapping("/api/instructor")
    public Instructor addInstructor(@RequestBody Instructor newInstructor) {
        return instructorRepository.save(newInstructor);
    }

    /**
     * Return all instructor entries stored in the database
     * 
     * @return All instructor entries in the database in JSON format
     */
    @GetMapping("/api/instructor")
    public List<Instructor> allInstructors() {
        return instructorRepository.findAll();
    }

    /**
     * Return the instructor matching the specified ID, if they exist
     * @param institutionId The institution ID for the instructor
     * @return The matching instructor, or a 404 response
     */
    @GetMapping("/api/instructor/by_id/{institutionId}")
    public Instructor getById(@PathVariable Long institutionId) {
        return instructorRepository.findByInstitutionId(institutionId).orElseThrow(() -> new UserNotFoundException(institutionId));
    }

    /**
     * Return the instructor teaching the specified section, if they exist
     * @param section The section of the required instructor
     * @return The matching instructor, or a 404 response
     */
    @GetMapping("/api/instructor/by_section/{section}")
    public Instructor getBySection(@PathVariable int section) {
        return instructorRepository.findBySectionsContaining(section).orElseThrow(() -> new UserNotFoundException());
    }
}
