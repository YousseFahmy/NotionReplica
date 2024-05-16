package com.notionreplica.udbs.services.command;

public interface CommandInterface {

    int CREATE_UDBDATATABLE = 0;
    int GET_UDBDATATABLE= 1;
    int UPDATE_UDBDATATABLE = 2;
    int ADD_PROPERTYTOUDBDATATABLE = 3;
    int REMOVE_PROPERTYFROMUDBDATATABLE = 4;
    int ADD_UDBPAGE = 5;
    int REMOVE_UDBPAGE = 6;
    int DELETE_UDBDATATABLE = 7;
    int CREATE_UDBPAGE = 8;
    int GET_UDBPAGE = 9;
    int UPDATE_UDBPAGE = 10;
    int DELETE_UDBPAGE = 11;

    Object execute() throws Exception;
}
