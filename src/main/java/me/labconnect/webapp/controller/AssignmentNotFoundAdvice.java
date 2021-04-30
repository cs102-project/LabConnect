package me.labconnect.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A configuration class describing the handling of AssignmentNotFoundException.
 * 
 * @author Berkan Şahin
 * @version 30.04.2021
 */
@ControllerAdvice
public class AssignmentNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String assignmentNotFoundHandler(AssignmentNotFoundException exception) {
        return exception.getMessage();
    }
}