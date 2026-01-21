package com.pragma.challenge.msvc_users.infrastructure.configuration.advisor;

import com.pragma.challenge.msvc_users.domain.exception.EntityAlreadyExistsException;
import com.pragma.challenge.msvc_users.domain.exception.EntityNotFoundException;
import com.pragma.challenge.msvc_users.domain.exception.UnderAgedUserException;
import com.pragma.challenge.msvc_users.infrastructure.configuration.advisor.response.ExceptionResponse;
import com.pragma.challenge.msvc_users.infrastructure.util.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFound(EntityNotFoundException e){
        return ExceptionResponseBuilder.buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEntityAlreadyExists(EntityAlreadyExistsException e){
        return ExceptionResponseBuilder.buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnderAgedUserException.class)
    public ResponseEntity<ExceptionResponse> handleUnderAgedUser(UnderAgedUserException e){
        return ExceptionResponseBuilder.buildResponse(e, HttpStatus.CONFLICT);
    }
}
