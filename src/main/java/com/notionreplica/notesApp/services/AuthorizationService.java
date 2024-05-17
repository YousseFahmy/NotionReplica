package com.notionreplica.notesApp.services;
import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.notionreplica.notesApp.services.command.CommandInterface.*;

@Service
public class AuthorizationService {
    @Autowired
    private CommandFactory CommandFactory;

    public  boolean isPageOwner(Workspace userWorkspace, String pageId) throws Exception{
        return (boolean) CommandFactory.create(IS_PAGE_OWNER,userWorkspace,pageId).execute();
    }

    public Workspace isWorkSpaceOwner(long userId,String workspaceId) throws Exception{
        return (Workspace) CommandFactory.create(GET_WORKSPACE,userId,workspaceId).execute();
    }

    public boolean isRequesterAuthorized(Workspace userWorkspace, long requesterId) throws Exception{
        return (boolean) CommandFactory.create(IS_REQUESTER_AUTHORIZED,userWorkspace,requesterId).execute();
    }
}
