package com.notionreplica.notesApp.services.command.read;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.InvalidObjectIdException;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Optional;

@AllArgsConstructor
public class GetPages implements CommandInterface {
    PageRepo pageRepo;
    String workspaceId;
    @Override
    public Object execute() throws Exception{
        if(!ObjectId.isValid(workspaceId)){
            throw new InvalidObjectIdException("invalid workspace id");
        }
        return pageRepo.findPagesByWorkspaceId(workspaceId);
    }
}
