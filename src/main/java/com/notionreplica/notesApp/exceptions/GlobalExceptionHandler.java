package com.notionreplica.notesApp.exceptions;

import com.notionreplica.notesApp.controller.NotesController;
import com.notionreplica.notesApp.controller.WorkspaceController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({WorkspaceController.class})
    public String handleWorkspaceNotFoundException(WorkspaceNotFoundException exception){
        return HttpStatus.INTERNAL_SERVER_ERROR+ exception.getMessage();
    }
}