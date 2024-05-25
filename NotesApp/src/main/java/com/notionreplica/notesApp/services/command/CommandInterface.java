package com.notionreplica.notesApp.services.command;

import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;

public interface CommandInterface {
    int CREATE_WORKSPACE=0;
    int CREATE_PAGE = 1;
    int GET_WORKSPACE =2;
    int GET_PAGES = 3;
    int GET_PAGE = 4;
    int GET_SHARED_PAGES=5;
    int DELETE_WORKSPACE = 6;
    int DELETE_PAGES=7;
    int DELETE_PAGE=8;
    int IS_WORKSPACE_OWNER = 9;
    int IS_PAGE_OWNER= 10;
    int IS_REQUESTER_AUTHORIZED =11;
    int ADD_USER_TO_WORKSPACE =12;
    int REMOVE_USER_FROM_WORKSPACE = 13;
    int UPDATE_PAGE_TITLE = 14;
    int UPDATE_PAGE_BACKGROUND=15;
    int UPDATE_PAGE_ICON=16;
    int UPDATE_PAGE_CONTNET = 17;
    int MOVE_SUBPAGE = 18;
    int CHANGE_ACCESS_MODIFEIR =19;
    int ADD_UDB = 20;
    int DELETE_UDB=21;
    Object execute() throws Exception;

}
