package com.notionreplica.userapp.exceptions;


public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String s)
    {
        // Call constructor of parent Exception
        super(s);
    }
}
