package com.notionreplica.notesApp.Services.command;

import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.notionreplica.notesApp.Services.command.CommandInterface.*;

@Component
public class CommandFactory {
    @Autowired
    private PageRepo pageRepo;
    @Autowired
    private WorkspaceRepo workspaceRepo;
    public CommandInterface create(int commandCode, Object... params){
        switch (commandCode){
            case CREATE_WORKSPACE:
                return new CreateWorkspace(workspaceRepo,(long)params[0]);
            case ADD_PAGE:
                return  new addPage(pageRepo,workspaceRepo);
            case GET_PAGES:
                return  new GetPages(pageRepo);
        }
        return null;
    }
}
