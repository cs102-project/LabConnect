package me.labconnect.webapp.controller;

import me.labconnect.webapp.models.testing.BadExampleException;
import me.labconnect.webapp.models.testing.TestResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * A configuration class describing the rules for exception handling in the Controller layer
 *
 * @author Berkan Şahin
 * @version 05.05.2021
 */
@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadExampleException.class)
    public TestResult badExampleHandler(BadExampleException e) {
        return e.getResult();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementHandler(NoSuchElementException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public String ioExceptionHandler(IOException e) {
        return e.getMessage();
    }
}
