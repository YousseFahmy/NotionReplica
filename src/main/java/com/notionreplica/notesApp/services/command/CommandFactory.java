package com.notionreplica.notesApp.services.command;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.create.CreatePage;
import com.notionreplica.notesApp.services.command.create.CreateWorkspace;
import com.notionreplica.notesApp.services.command.read.GetPages;
import com.notionreplica.notesApp.services.command.read.GetWorkspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.notionreplica.notesApp.services.command.CommandInterface.*;

@Component
public class CommandFactory {
    @Autowired
    private PageRepo pageRepo;
    @Autowired
    private WorkspaceRepo workspaceRepo;
    public CommandInterface create(int commandCode, Object... params){
        switch (commandCode){
            case GET_WORKSPACE:
                return new GetWorkspace(workspaceRepo,(long) params[0]);
            case CREATE_WORKSPACE:
                return new CreateWorkspace(workspaceRepo,(long)params[0]);
            case CREATE_PAGE:
                return  new CreatePage(pageRepo,workspaceRepo,(String) params[0],(AccessModifier) params[1],(String)params[2]);
            case GET_PAGES:
                return  new GetPages(pageRepo,(String) params[0]);
        }
        return null;
    }
}
