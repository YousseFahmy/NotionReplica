package com.notionreplica.searchapp.services.command;

public interface CommandInterface {
        int SEARCH_PAGES = 0;
        int FILTER_IN = 1;
        int SEARCH_UDB = 2;
        int SORT = 3;
    Object execute() throws Exception;

}
