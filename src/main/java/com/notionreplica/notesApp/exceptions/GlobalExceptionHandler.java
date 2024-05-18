package com.notionreplica.notesApp.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(InvalidObjectIdException.class)
    public ResponseEntity<String> handleInvalidObjectIdException(InvalidObjectIdException ex) {
        return new ResponseEntity<>("The provided object id provided isn't in a proper format", HttpStatus.I_AM_A_TEAPOT);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WorkspaceNotFoundException.class)
    public ResponseEntity<String> handleWorkspaceNotFoundException(WorkspaceNotFoundException ex) {
        return new ResponseEntity<>("The workspace id provided doesn't exist", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> handleUserDoesNotExistException(UserDoesNotExistException ex) {
        return new ResponseEntity<>("The user provided doesn't exist", HttpStatus.NOT_FOUND);
    }

}