package com.notionreplica.notesApp.exceptions;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String s) {
        super(s);
    }
}
