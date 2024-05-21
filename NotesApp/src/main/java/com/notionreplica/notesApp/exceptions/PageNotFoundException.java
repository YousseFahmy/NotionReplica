package com.notionreplica.notesApp.exceptions;

public class PageNotFoundException extends Exception{
    public PageNotFoundException(String s)
    {
        // Call constructor of parent Exception
        super(s);
    }
}