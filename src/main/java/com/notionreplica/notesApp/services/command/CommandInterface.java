package com.notionreplica.notesApp.services.command;

import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;

public interface CommandInterface {

    int GET_WORKSPACE =0;
    int CREATE_WORKSPACE=1;
    int GET_PAGES = 2;
    int CREATE_PAGE = 3;
    int GET_PAGE = 4;
    int DELETE_WORKSPACE = 5;
    int DELETE_PAGES=6;

    Object execute() throws Exception;
}
