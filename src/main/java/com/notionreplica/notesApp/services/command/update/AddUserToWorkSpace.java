package com.notionreplica.notesApp.services.command.create;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class AddUserToWorkSpace implements CommandInterface {
    WorkspaceRepo workspaceRepo;
    UUID userId;
    UUID newUserId;

    @Override
    public Object execute() throws Exception {
        Workspace userWorkspace = workspaceRepo.findWorkspaceByUserId(userId);
        if(userWorkspace == null) throw new WorkspaceNotFoundException("The user id doesn't have a workspace");
        userWorkspace.getUsersWithAccess().add(newUserId);
        return workspaceRepo.save(userWorkspace);
    }
}