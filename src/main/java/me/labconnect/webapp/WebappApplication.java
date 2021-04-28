package me.labconnect.webapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.models.Instructor;
import me.labconnect.webapp.models.LabAssignment;
import me.labconnect.webapp.models.Student;
import me.labconnect.webapp.models.TeachingAssistant;
import me.labconnect.webapp.models.User;
import me.labconnect.webapp.testing.Tester;
import me.labconnect.webapp.testing.style.IndentationChecker;

@SpringBootApplication
@RestController
public class WebappApplication {

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

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

	@GetMapping("/api/dummy")
	public String dummyWorkflow() throws IOException {
		TeachingAssistant tempTA;
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
		tempTA = assistantRepository.findByInstitutionID(1234);
		
		tempTA.getStudents().add(studentRepository.findByInstitutionID(22003211));

		assistantRepository.save(tempTA); // Save and retrieve the object for every atomic operation!

		// Make a new assignment
		dummyInstruction = Files.createTempFile(null, ".pdf");
		dummyTesters = new ArrayList<>();
		dummyTesters.add(new IndentationChecker());

		dummyAssignment = new LabAssignment("Minecraft", new GregorianCalendar(2021, 05, 06).getTime(), true,
				dummyInstruction, dummyTesters, new int[] { 1, 2, 3 });

		assignmentRepository.save(dummyAssignment);	
		return "Success!\n";
	}
}
