package me.labconnect.webapp.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import me.labconnect.webapp.controller.InstructorAPI;
import me.labconnect.webapp.models.users.Instructor;

/**
 * A componenet class that turns Instructor models into RESTful entities
 * 
 * @author Berkan Şahin
 * @version 29.04.2021
 */
@Component
public class InstructorModelAssembler implements RepresentationModelAssembler<Instructor, EntityModel<Instructor>> {

    /**
     * A method that creates an entity model from an Instructor class
     * 
     * @param instructor The instructor to convert
     * @return An entity model of an instructor
     */
    @Override
    public EntityModel<Instructor> toModel(Instructor instructor) {
        return EntityModel.of(instructor,
                linkTo(methodOn(InstructorAPI.class).getById(instructor.getInstitutionId())).withSelfRel(),
                linkTo(methodOn(InstructorAPI.class).allInstructors()).withRel("instructors"));
    }

}
