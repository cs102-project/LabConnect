package me.labconnect.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A configuration class describing the handling of custom exceptions
 * 
 * @author Berkan Şahin
 * @version 30.04.2021
 */
@ControllerAdvice
public class ExceptionHandlingAdvice {

    /**
     * Describes the handling of AssignmentNotFoundException
     * 
     * @param exception The exception to handle
     * @return A 404 Response containing the message passed by the exception
     */
    @ResponseBody
    @ExceptionHandler(AssignmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String assignmentNotFoundHandler(AssignmentNotFoundException exception) {
        return exception.getMessage();
    }

    /**
     * Describes the handling of UserNotFoundException
     * 
     * @param exception The exception to handle
     * @return A 404 Response containing the message passed by the exception
     */
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException exception) {
        return exception.getMessage();
    }
}