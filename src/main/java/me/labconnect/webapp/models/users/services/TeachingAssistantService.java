package me.labconnect.webapp.models.users.services;

import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.repository.TARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serves different teaching assistant functions for controllers
 *
 * @author Vedat Eren Arican
 * @version 03.05.2021
 */
@Service
public class TeachingAssistantService {

    @Autowired
    private TARepository taRepository;

    /**
     * Adds the given student to specified teaching assistant
     *
     * @param assistant Teaching assistant to which the student will be added
     * @param student   Student to be added to the teaching assistant
     */
    public void addStudent(TeachingAssistant assistant, Student student) {

        assistant.getStudents().add(student.getId());
        taRepository.save(assistant);

    }


}
