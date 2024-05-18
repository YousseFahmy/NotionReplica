package com.notionreplica.notesApp.services.command.create;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CreateWorkspace implements CommandInterface {
    WorkspaceRepo workRepo;
    UUID userId;
    @Override
    public Object execute() {
        Workspace userWorkspaceExists = workRepo.findWorkspaceByUserId(userId);
        if (userWorkspaceExists==null) {
        Workspace newWorkSpace = new Workspace(userId);
        return workRepo.save(newWorkSpace);}
        return userWorkspaceExists;
    }
}
