package com.notionreplica.userapp.exceptions;

public class IncorrectCredentialsException extends Exception{
    public IncorrectCredentialsException(String s)
    {
        // Call constructor of parent Exception
        super(s);
    }
}
