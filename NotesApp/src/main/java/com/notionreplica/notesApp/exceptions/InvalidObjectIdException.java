package com.notionreplica.notesApp.exceptions;

public class InvalidObjectIdException extends Exception{
    public InvalidObjectIdException(String s)
    {
        // Call constructor of parent Exception
        super(s);
    }
}
