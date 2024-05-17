package com.notionreplica.notesApp.services.command.Authorize;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.nio.file.AccessDeniedException;

@AllArgsConstructor
public class IsRequesterAuthorized implements CommandInterface {
    Workspace userWorkSpace;
    long requesterId;

    @Override
    public Object execute() throws Exception {
        if(!userWorkSpace.getUsersWithAccess().contains(requesterId)){
            throw new AccessDeniedException("");
        }
        return true;
    }
}
