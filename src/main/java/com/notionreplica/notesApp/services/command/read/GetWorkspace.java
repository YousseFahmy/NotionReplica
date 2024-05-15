package com.notionreplica.notesApp.services.command.read;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GetWorkspace implements CommandInterface {
    WorkspaceRepo workRepo;
    long userId;
    @Override
    public Object execute() throws Exception {
        Workspace userWorkspaceExists = workRepo.findWorkspaceByUserId(userId);
        if(userWorkspaceExists==null) throw new WorkspaceNotFoundException("the user id doesnt have a workspace");
        return userWorkspaceExists;
    }
}
