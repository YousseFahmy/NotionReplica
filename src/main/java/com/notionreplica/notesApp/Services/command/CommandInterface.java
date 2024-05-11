package com.notionreplica.notesApp.Services.command;

public interface CommandInterface {
    int CREATE_WORKSPACE=0;
    int ADD_PAGE = 1;
    int GET_PAGES = 2;

    Object execute();
}
