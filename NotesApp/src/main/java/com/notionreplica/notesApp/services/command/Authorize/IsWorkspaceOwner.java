package com.notionreplica.notesApp.services.command.Authorize;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.InvalidObjectIdException;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@AllArgsConstructor
public class IsWorkspaceOwner implements CommandInterface {
    WorkspaceRepo workRepo;
    String userName;
    String workspaceId;
    @Override
    public Object execute() throws Exception {
        if(!ObjectId.isValid(workspaceId)){
            throw new InvalidObjectIdException("invalid workspace id");
        }
        Workspace userWorkspaceExists = workRepo.findWorkspaceByUserName(userName);
        if(userWorkspaceExists==null) throw new WorkspaceNotFoundException("no workspace for user " + userName+ "was found");
        if(!userWorkspaceExists.getWorkSpaceId().equals((workspaceId)))  throw new AccessDeniedException("the user with username "+ userName+ "tried accessing the workspace:"+userWorkspaceExists.getWorkSpaceId() +"and they werent authorzied to do so");;
        return userWorkspaceExists;
    }
}
