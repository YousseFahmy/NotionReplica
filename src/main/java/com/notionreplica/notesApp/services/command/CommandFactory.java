package com.notionreplica.notesApp.services.command;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.Authorize.IsRequesterAuthorized;
import com.notionreplica.notesApp.services.command.Authorize.IsWorkspaceOwner;
import com.notionreplica.notesApp.services.command.Authorize.IsPageOwner;
import com.notionreplica.notesApp.services.command.create.AddUserToWorkSpace;
import com.notionreplica.notesApp.services.command.create.CreatePage;
import com.notionreplica.notesApp.services.command.create.CreateWorkspace;
import com.notionreplica.notesApp.services.command.delete.deletePage;
import com.notionreplica.notesApp.services.command.delete.deletePages;
import com.notionreplica.notesApp.services.command.delete.deleteWorkSpace;
import com.notionreplica.notesApp.services.command.read.GetPage;
import com.notionreplica.notesApp.services.command.read.GetPages;
import com.notionreplica.notesApp.services.command.read.GetSharedPages;
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
            case CREATE_WORKSPACE:
                return new CreateWorkspace(workspaceRepo,(long)params[0]);
            case CREATE_PAGE:
                return  new CreatePage(pageRepo,workspaceRepo,(String) params[0],(AccessModifier) params[1],(String)params[2]);
            case GET_WORKSPACE:
                return new GetWorkspace(workspaceRepo,(long) params[0]);
            case GET_PAGES:
                return  new GetPages(pageRepo,(String) params[0]);
            case GET_PAGE:
                return new GetPage(pageRepo,workspaceRepo,(String)params[0],(String) params[1]);
            case GET_SHARED_PAGES:
                return new GetSharedPages(pageRepo,(Workspace)params[0]);
            case DELETE_WORKSPACE:
                return new deleteWorkSpace(workspaceRepo, (long) params[0]);
            case DELETE_PAGES:
                return new deletePages(pageRepo,workspaceRepo, (String) params[0]);
            case DELETE_PAGE:
                return new deletePage(workspaceRepo,(String)params[0],(String)params[1]);
            case IS_WORKSPACE_OWNER:
                return new IsWorkspaceOwner(workspaceRepo,(long)params[0],(String) params[1]);
            case IS_PAGE_OWNER:
                return new IsPageOwner((Workspace) params[0],(String) params[1]);
            case IS_REQUESTER_AUTHORIZED:
                return new IsRequesterAuthorized((Workspace) params[0],(long) params[1]);
            case ADD_USER_TO_WORKSPACE:
                return new AddUserToWorkSpace(workspaceRepo,(long)params[0],(long)params[1]);
        }
        return null;
    }




}
