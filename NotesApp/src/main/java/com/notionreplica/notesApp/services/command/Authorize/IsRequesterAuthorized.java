package com.notionreplica.notesApp.services.command.Authorize;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@AllArgsConstructor
public class IsRequesterAuthorized implements CommandInterface {
    Workspace userWorkSpace;
    String requesterName;

    @Override
    public Object execute() throws Exception {
        if(!userWorkSpace.getUsersWithAccess().contains(requesterName)){
            throw new AccessDeniedException("the requester with username "+ requesterName+ "tried accessing the shared pages of "+userWorkSpace.getWorkSpaceId() +"and they werent authorzied to do so");
        }
        return true;
    }
}
