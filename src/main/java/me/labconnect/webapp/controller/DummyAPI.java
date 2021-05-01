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
import me.labconnect.webapp.models.testing.BadExampleException;
import me.labconnect.webapp.models.testing.Tester;
import me.labconnect.webapp.models.testing.UnitTest;
import me.labconnect.webapp.models.testing.style.IndentationChecker;
import me.labconnect.webapp.models.testing.style.ProgramHeaderJavadocChecker;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.AttemptRepository;
import me.labconnect.webapp.repository.InstructorRepository;
import me.labconnect.webapp.repository.StudentRepository;
import me.labconnect.webapp.repository.TARepository;

/**
 * API for testing core functions of the project
 *
 * @author Berkan Şahin
 * @version 30.04.2021
 */
@RestController
public class DummyAPI {
	// Testing fields
	@Autowired // For dependecy injection (from spring side)
	private InstructorRepository instructorRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AssignmentRepository assignmentRepository;
	@Autowired
	private TARepository assistantRepository;
	@Autowired
	private AttemptRepository attemptRepository;

	/**
	 * Submit an attempt for the assignment with the given ID as the student with
	 * the given student ID
	 *
	 * @param attemptPath  The path to the attempt ZIP
	 * @param studentId    The ID of the student submitting this attempt
	 * @param assignmentId The ID of the assignment
	 * @return The output of the unit/style tests
	 * @throws IOException If processing the attempt fails
	 */
	@GetMapping("/api/dummy/submit")
	public String dummyWorkflow(@RequestParam(name = "attempt") String attemptPath,
			@RequestParam(name = "student_id") Long studentId,
			@RequestParam(name = "assignment_id") String assignmentId) throws IOException {
		Student tempStudent;
		List<String> allTestOutputs;
		Assignment dummyAssignment;
		Submission submission;
		Attempt attempt;
		String testOut;

		dummyAssignment = assignmentRepository.findByAssignmentID(assignmentId)
				.orElseThrow(() -> new AssignmentNotFoundException(assignmentId));

		// Let's try submitting an attempt
		tempStudent = studentRepository.findByInstitutionId(studentId)
				.orElseThrow(() -> new UserNotFoundException(studentId));

		submission = tempStudent.getSubmissionFor(dummyAssignment);
		attempt = attemptRepository.save(new Attempt(Paths.get(attemptPath), submission));
		tempStudent.getSubmissionFor(dummyAssignment).addAttempt(attempt);
		studentRepository.save(tempStudent);

		testOut = String.format("<p class=attemptId>Attempt ID: %s</p>", attempt.getAttemptID());

		allTestOutputs = attempt.getTestResults().stream().flatMap(r -> r.getOutput().stream())
				.collect(Collectors.toList());

		for (String outputLine : allTestOutputs) {
			testOut += String.format("<p class=testOut>%s</p>", outputLine);
		}
		return testOut;
	}

	/**
	 * Assign an assignment to a student
	 *
	 * @param studentID    The ID of the student this assignment is for
	 * @param assignmentID The ID of the assignment
	 * @return "Success" upon successful assignment
	 */
	@GetMapping("/api/dummy/assign_to_student")
	public String assignToStudent(@RequestParam(name = "student_id") Long studentID,
			@RequestParam(name = "assignment_id") String assignmentID) {
		Assignment assignment;
		Student student;

		assignment = assignmentRepository.findByAssignmentID(assignmentID)
				.orElseThrow(() -> new AssignmentNotFoundException(assignmentID));
		student = studentRepository.findByInstitutionId(studentID)
				.orElseThrow(() -> new UserNotFoundException(studentID));

		student.giveAssignment(assignment);
		student.addSubmission(assignment, new Submission(student, assignment));

		studentRepository.save(student);

		return "Success!";
	}

	/**
	 * Add a unit test for the given assignment
	 *
	 * @param assignmentID The unique ID of the assignment
	 * @param testerClass  The tester class for the unit test, a single .java file
	 * @param exampleImpl  The example implementation, path to a ZIP file of the
	 *                     contents of src/
	 * @param testerName   The name of the unit test
	 * @return A success message upon a successful unit test
	 * @throws IOException         If processing the tester class or the example
	 *                             implementation fails
	 * @throws BadExampleException If the example implementation does not compile or
	 *                             if it generates a runtime error (or a non-zero
	 *                             exit code)
	 */
	@GetMapping("/api/dummy/add_unit_test")
	public String addUnitTest(@RequestParam(name = "assignment_id") String assignmentID,
			@RequestParam(name = "tester") String testerClass, @RequestParam(name = "example") String exampleImpl,
			@RequestParam(name = "name") String testerName) throws IOException, BadExampleException {
		Assignment dummyAssignment;

		dummyAssignment = assignmentRepository.findByAssignmentID(assignmentID)
				.orElseThrow(() -> new AssignmentNotFoundException(assignmentID));

		dummyAssignment
				.addTest(new UnitTest(testerName, Paths.get(testerClass), Paths.get(exampleImpl), dummyAssignment));
		dummyAssignment = assignmentRepository.save(dummyAssignment);

		return "<p>Added test successfully!</p>";

	}

	/**
	 * Initializes a dummy assignment with the given name
	 *
	 * @param assignmentName The name of the assignment
	 * @return The unique assignment ID upon succesful initialization
	 * @throws IOException If an I/O error occurs
	 */
	@GetMapping("/api/dummy/assignment_init")
	public String initializeAssignment(@RequestParam(name = "name") String assignmentName) throws IOException {
		final String RESPONSE_TEMPLATE = "<p class=assignmentID> Assignment ID: %s</p>";
		Path dummyInstruction;
		ArrayList<Tester> dummyTesters;
		Assignment dummyAssignment;
		String assignmentId;

		// Make a new assignment
		dummyInstruction = Files.createTempFile(null, ".pdf");
		dummyTesters = new ArrayList<>();
		dummyTesters.add(new IndentationChecker());

		dummyAssignment = new LabAssignment(assignmentName, new GregorianCalendar(2021, 05, 06).getTime(), true,
				dummyInstruction, dummyTesters, new int[] { 1, 2, 3 });

		assignmentId = assignmentRepository.save(dummyAssignment).getAssignmentID();

		return String.format(RESPONSE_TEMPLATE, assignmentId);

	}

	/**
	 * Add a TA, an Instructor and a Student with predetermined properties to
	 * initialize the database
	 *
	 * @return "Success" upon successful initialization
	 */
	@GetMapping("/api/dummy/user_init")
	public String initializeUsers() {
		TeachingAssistant temproraryTA;

		// Add our users
		instructorRepository.save(new Instructor("David Davenport", 1, "Computer Science", new int[] { 1, 2, 3 }));
		studentRepository.save(new Student("Linus Torvalds", 22003211, "Computer Science", 1));
		assistantRepository.save(new TeachingAssistant("Richard Matthew Stallman", 1234, "Computer Science",
				new ArrayList<Student>(), 1));

		// Assign the student to our TA
		temproraryTA = assistantRepository.findByInstitutionId(1234).orElseThrow();

		temproraryTA.getStudents().add(studentRepository.findByInstitutionId(22003211).orElseThrow());

		assistantRepository.save(temproraryTA); // Save and retrieve the object for every atomic operation!

		return "Success\n";
	}

}
