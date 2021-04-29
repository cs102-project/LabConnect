package me.labconnect.webapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.models.*;
import me.labconnect.webapp.models.testing.Tester;
import me.labconnect.webapp.models.testing.style.IndentationChecker;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.InstructorRepository;
import me.labconnect.webapp.repository.StudentRepository;
import me.labconnect.webapp.repository.TARepository;
import me.labconnect.webapp.repository.UserRepository;

@RestController
public class DummyAPI {

	@Autowired
	private UserRepository<User> userRepository;
	@Autowired
	private InstructorRepository instructorRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AssignmentRepository assignmentRepository;
	@Autowired
	private TARepository assistantRepository;

	@GetMapping("/api/dummy")
	public String dummyWorkflow() throws IOException {
		TeachingAssistant tempTA;
		Student tempStudent;
		Path dummyInstruction;
		ArrayList<Tester> dummyTesters;
		LabAssignment dummyAssignment;

		userRepository.deleteAll();
		assignmentRepository.deleteAll();

		// Add our users
		instructorRepository.save(new Instructor("David Davenport", 1, "Computer Science", new int[] { 1, 2, 3 }));
		studentRepository.save(new Student("Cemudes Torno", 22003211, "Computer Science", 1));
		assistantRepository.save(new TeachingAssistant("Richard Matthew Stallman", 1234, "Computer Science",
				new ArrayList<Student>(), 1));

		// Assign the student to our TA
		tempTA = assistantRepository.findByInstitutionId(1234).orElseThrow();

		tempTA.getStudents().add(studentRepository.findByInstitutionId(22003211).orElseThrow());

		assistantRepository.save(tempTA); // Save and retrieve the object for every atomic operation!

		// Make a new assignment
		dummyInstruction = Files.createTempFile(null, ".pdf");
		dummyTesters = new ArrayList<>();
		dummyTesters.add(new IndentationChecker());

		dummyAssignment = new LabAssignment("Minecraft", new GregorianCalendar(2021, 05, 06).getTime(), true,
				dummyInstruction, dummyTesters, new int[] { 1, 2, 3 });

		assignmentRepository.save(dummyAssignment);

		tempStudent = studentRepository.findByInstitutionId(22003211).orElseThrow();

		for (Assignment assignment : assignmentRepository.findBySectionsContaining(tempStudent.getSection())) {
			tempStudent.giveAssignment(assignment);
		}
		studentRepository.save(tempStudent);

		return "Success!\n";
	}


}
