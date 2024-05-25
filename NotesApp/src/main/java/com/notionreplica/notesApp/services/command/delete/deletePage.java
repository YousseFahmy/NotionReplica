package com.notionreplica.notesApp.services.command.delete;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class deletePage implements CommandInterface {
    WorkspaceRepo workspaceRepo;
    String pageId;
    String workspaceId;
    @Override
    public Object execute() throws Exception {
        Optional<Workspace>userWorkspaceExists = workspaceRepo.findById(workspaceId);
        if(!userWorkspaceExists.isPresent()) throw new WorkspaceNotFoundException("The worksspace"+ workspaceId +" doesn't exist");
        userWorkspaceExists.get().getAccessModifiers().put(pageId, AccessModifier.DELETED);
        workspaceRepo.save(userWorkspaceExists.get());
        return "deleted page " +pageId;
    }
}
