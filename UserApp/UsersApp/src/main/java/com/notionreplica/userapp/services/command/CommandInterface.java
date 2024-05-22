package com.notionreplica.userapp.services.command;

public interface CommandInterface {
    int SIGNUP = 0;
    int DELETE_USER = 1;
    int GET_USER = 2;
    int LOGIN = 3;
    int CHANGE_EMAIL = 4;
    int CHANGE_NAME = 5;
    int CHANGE_PASSWORD = 6;
    int CHANGE_USERNAME = 7;

    public Object execute() throws Exception;

}
