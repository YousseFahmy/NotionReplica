package com.notionreplica.notesApp.exceptions;

public class WorkspaceNotFoundException extends Exception{
    public WorkspaceNotFoundException(String s)
    {
        // Call constructor of parent Exception
        super(s);
    }
}
