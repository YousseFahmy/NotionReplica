package com.notionreplica.notesApp.services.command.read;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.InvalidObjectIdException;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@AllArgsConstructor
public class GetPage implements CommandInterface {
    PageRepo pageRepo;
    WorkspaceRepo workRepo;
    String pageId;
    String workspaceId;
    @Override
    public Object execute() throws Exception {
        if(!ObjectId.isValid(pageId)){
            throw new InvalidObjectIdException("invalid page id");
        }
        if(!ObjectId.isValid(workspaceId)){
            throw new InvalidObjectIdException("invalid workspace id");
        }
        Optional<Workspace> workspaceExists=workRepo.findById(workspaceId);
        if(!workspaceExists.isPresent()){
            throw new WorkspaceNotFoundException("this workspace id does not exist");
        }

        Workspace userWorkspace=workspaceExists.get();
        if(userWorkspace.getAccessModifiers().get(pageId)==null) {
           throw new AccessDeniedException("this workspace does not contain this page");
        }

        return pageRepo.findById(pageId).get();
    }
}
