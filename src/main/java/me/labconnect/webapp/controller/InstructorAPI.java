package me.labconnect.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.controller.assembler.InstructorModelAssembler;
import me.labconnect.webapp.models.users.Instructor;
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

    @Autowired
    private InstructorModelAssembler assembler;

    /**
     * Add the given instructor entry to the database
     * 
     * @param newInstructor The new instructor entry in JSON format
     * @return The newly added instructor entry
     */
    @PostMapping("/api/instructor")
    public EntityModel<Instructor> addInstructor(@RequestBody Instructor newInstructor) {
        Instructor instructor;
        instructor = instructorRepository.save(newInstructor);

        return assembler.toModel(instructor);
    }

    /**
     * Return all instructor entries stored in the database
     * 
     * @return All instructor entries in the database in JSON format
     */
    @GetMapping("/api/instructor/all")
    public CollectionModel<EntityModel<Instructor>> allInstructors() {
        List<EntityModel<Instructor>> instructorModels;

        instructorModels = instructorRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(instructorModels, linkTo(methodOn(InstructorAPI.class).allInstructors()).withSelfRel());
    }

    /**
     * Return the instructor matching the specified ID, if they exist
     * 
     * @param institutionId The institution ID for the instructor
     * @return The matching instructor, or a 404 response
     */
    @GetMapping("/api/instructor/by_id/{institutionId}")
    public EntityModel<Instructor> getById(@PathVariable Long institutionId) {
        Instructor instructor;
        instructor = instructorRepository.findByInstitutionId(institutionId)
                .orElseThrow(() -> new UserNotFoundException(institutionId));

        return assembler.toModel(instructor);
    }

    /**
     * Return the instructor teaching the specified section, if they exist
     * 
     * @param section The section of the required instructor
     * @return The matching instructor, or a 404 response
     */
    @GetMapping("/api/instructor/by_section/{section}")
    public EntityModel<Instructor> getBySection(@PathVariable int section) {
        Instructor instructor;
        instructor = instructorRepository.findBySectionsContaining(section).orElseThrow(() -> new UserNotFoundException());

        return assembler.toModel(instructor);
    }
}
