package me.labconnect.webapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.LabAssignment;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.testing.Tester;
import me.labconnect.webapp.models.testing.style.IndentationChecker;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.AttemptRepository;
import me.labconnect.webapp.repository.InstructorRepository;
import me.labconnect.webapp.repository.StudentRepository;
import me.labconnect.webapp.repository.TARepository;

@RestController
public class DummyAPI {

	@Autowired
	private InstructorRepository instructorRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AssignmentRepository assignmentRepository;
	@Autowired
	private TARepository assistantRepository;
	@Autowired
	private AttemptRepository attemptRepository;

	@GetMapping("/api/dummy")
	public String dummyWorkflow(@RequestParam(name="attempt") String attemptPath) throws IOException {
		TeachingAssistant tempTA;
		Student tempStudent;
		Path dummyInstruction;
		ArrayList<Tester> dummyTesters;
		List<String> allTestOutputs;
		Assignment dummyAssignment;
		String assignmentId;
		Submission submission;
		Attempt attempt;
		String testOut;

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


		assignmentId = assignmentRepository.save(dummyAssignment).getAssignmentID();

		tempStudent = studentRepository.findByInstitutionId(22003211).orElseThrow();

		for (Assignment assignment : assignmentRepository.findBySectionsContaining(tempStudent.getSection())) {
			tempStudent.giveAssignment(assignment);
		}
		studentRepository.save(tempStudent);

		tempStudent = studentRepository.findByInstitutionId(22003211).orElseThrow();
		dummyAssignment = assignmentRepository.findByAssignmentID(assignmentId).orElseThrow();
		if (!tempStudent.addSubmission(dummyAssignment, new Submission(tempStudent, dummyAssignment)))
			return "Assignments do not match!\n";
		studentRepository.save(tempStudent);

		// Let's try submitting an attempt
		tempStudent = studentRepository.findByInstitutionId(22003211).orElseThrow();

		submission = tempStudent.getSubmissionFor(dummyAssignment);
		attempt = attemptRepository.save(new Attempt(Paths.get(attemptPath), submission));
		tempStudent.getSubmissionFor(dummyAssignment).addAttempt(attempt);
		studentRepository.save(tempStudent);

		testOut = String.format("<p class=assignmentID>Assignment ID: %s</p>", assignmentId);
		
		allTestOutputs = attempt.getTestResults().stream().flatMap(r -> r.getOutput().stream()).collect(Collectors.toList());

		for (String outputLine : allTestOutputs) {
			testOut += String.format("<p class=testOut>%s</p>", outputLine);
		}
		return testOut;
	}


}
