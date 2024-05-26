package com.notionreplica.searchapp.services.command;

import com.notionreplica.searchapp.repositories.PageRepo;
import com.notionreplica.searchapp.repositories.WorkspaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.notionreplica.searchapp.services.command.CommandInterface.*;

@Component
public class CommandFactory {
    @Autowired
    private PageRepo pageRepo;
    @Autowired
    private WorkspaceRepo workspaceRepo;

    public CommandInterface create(int commandCode, Object... params){
        switch (commandCode){
            case SEARCH_PAGES:
                return new SearchPages(workspaceRepo, pageRepo, (String) params[0], (String) params[1]);
            case FILTER_IN:
                return new FilterIn(workspaceRepo, pageRepo, (String) params[0], (String) params[1], (String) params[2]);
            case SORT:
                return new Sort(workspaceRepo, pageRepo, (String) params[0], (String) params[1], (String) params[2]);
        }
        return null;
    }




}
