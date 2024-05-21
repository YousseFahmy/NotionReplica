package com.notionreplica.udbs.services.command;

public interface CommandInterface {

    int CREATE_UDBDATATABLE = 0;
    int GET_UDBDATATABLE= 1;
    int UPDATE_UDBDATATABLE = 2;
    int ADD_PROPERTYTOUDBDATATABLE = 3;
    int REMOVE_PROPERTYFROMUDBDATATABLE = 4;
    int ADD_UDBPAGETOTABLE = 5;
    int REMOVE_UDBPAGEFROMTABLE = 6;
    int DELETE_UDBDATATABLE = 7;
    int CREATE_UDBPAGE = 8;
    int GET_UDBPAGE = 9;
    int GET_UDBPAGES = 10;
    int UPDATE_UDBPAGE = 11;
    int DELETE_UDBPAGE = 12;

    int CREATE_PROPERTY = 13;
    int GET_PROPERTY = 14;
    int GET_PROPERTIES = 15;
    int UPDATE_PROPERTY = 16;
    int DELETE_PROPERTY = 17;

    Object execute() throws Exception;
}
