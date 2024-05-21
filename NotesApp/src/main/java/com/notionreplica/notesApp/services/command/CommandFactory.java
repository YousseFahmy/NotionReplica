package com.notionreplica.notesApp.services.command;

import com.notionreplica.notesApp.entities.*;
import com.notionreplica.notesApp.repositories.*;
import com.notionreplica.notesApp.services.command.Authorize.*;
import com.notionreplica.notesApp.services.command.create.*;
import com.notionreplica.notesApp.services.command.update.*;
import com.notionreplica.notesApp.services.command.delete.*;
import com.notionreplica.notesApp.services.command.read.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
                return new CreateWorkspace(workspaceRepo,(String)params[0]);
            case CREATE_PAGE:
                return  new CreatePage(pageRepo,workspaceRepo,(String) params[0],(AccessModifier) params[1],(String)params[2]);
            case GET_WORKSPACE:
                return new GetWorkspace(workspaceRepo,(String) params[0]);
            case GET_PAGES:
                return  new GetPages(pageRepo,(String) params[0]);
            case GET_PAGE:
                return new GetPage(pageRepo,workspaceRepo,(String)params[0],(String) params[1]);
            case GET_SHARED_PAGES:
                return new GetSharedPages(pageRepo,(Workspace)params[0]);
            case DELETE_WORKSPACE:
                return new deleteWorkSpace(workspaceRepo,pageRepo, (String) params[0]);
            case DELETE_PAGES:
                return new deletePages(pageRepo,workspaceRepo, (String) params[0]);
            case DELETE_PAGE:
                return new deletePage(workspaceRepo,(String)params[0],(String)params[1]);
            case IS_WORKSPACE_OWNER:
                return new IsWorkspaceOwner(workspaceRepo,(String)params[0],(String) params[1]);
            case IS_PAGE_OWNER:
                return new IsPageOwner((Workspace) params[0],(String) params[1]);
            case IS_REQUESTER_AUTHORIZED:
                return new IsRequesterAuthorized((Workspace) params[0],(String) params[1]);
            case ADD_USER_TO_WORKSPACE:
                return new AddUserToWorkSpace(workspaceRepo,(String)params[0],(String)params[1]);
            case REMOVE_USER_FROM_WORKSPACE:
                return new RemoveUserFromWorkSpace(workspaceRepo,(String)params[0],(String)params[1]);
            case UPDATE_PAGE_TITLE:
                return new UpdatePageTitle(pageRepo,(String)params[0],(String)params[1]);
            case UPDATE_PAGE_BACKGROUND:
                return new UpdatePageBackground(pageRepo,(String)params[0],(String)params[1]);
            case UPDATE_PAGE_ICON:
                return new UpdatePageIcon(pageRepo,(String)params[0],(String)params[1]);
            case UPDATE_PAGE_CONTNET:
                return  new UpdatePageContent(pageRepo,(String)params[0],(List<ContentBlock>)params[1]);
            case MOVE_SUBPAGE:
                return new MoveSubPage(pageRepo,(String)params[0],(String)params[1],(String)params[2]);
            case CHANGE_ACCESS_MODIFEIR:
                return  new ChangeAccessModifeir(workspaceRepo,(Workspace)params[0],(AccessModifier)params[1],(String)params[2]);
        }
        return null;
    }




}
