package com.coderdojo.zen.award.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Hero is the main entity we'll be using to . . .
 * Please see the class for true identity
 * @author Captain America
 */
@ControllerAdvice
public class AwardNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AwardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(AwardNotFoundException ex) {
        return ex.getMessage();
    }
}
