package com.notionreplica.notesApp.exceptions;


import com.notionreplica.notesApp.controller.WorkspaceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(InvalidObjectIdException.class)
    public ResponseEntity<String> handleInvalidObjectIdException(InvalidObjectIdException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("The provided object id provided isn't in a proper format", HttpStatus.I_AM_A_TEAPOT);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WorkspaceNotFoundException.class)
    public ResponseEntity<String> handleWorkspaceNotFoundException(WorkspaceNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("The workspace id provided doesn't exist", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<String> handlePageNotFoundException(PageNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("The page id provided doesn't exist", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> handleUserDoesNotExistException(UserDoesNotExistException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("The user provided doesn't exist", HttpStatus.NOT_FOUND);
    }

}