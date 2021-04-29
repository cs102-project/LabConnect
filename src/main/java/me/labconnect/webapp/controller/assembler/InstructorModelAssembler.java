package me.labconnect.webapp.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import me.labconnect.webapp.controller.InstructorAPI;
import me.labconnect.webapp.models.Instructor;

@Component
public class InstructorModelAssembler implements RepresentationModelAssembler<Instructor, EntityModel<Instructor>> {

    @Override
    public EntityModel<Instructor> toModel(Instructor instructor) {
        return EntityModel.of(instructor,
                linkTo(methodOn(InstructorAPI.class).getById(instructor.getInstitutionId())).withSelfRel(),
                linkTo(methodOn(InstructorAPI.class).allInstructors()).withRel("instructors"));
    }

}
