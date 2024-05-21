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
    String userName;
    @Override
    public Object execute() {
        Workspace userWorkspaceExists = workRepo.findWorkspaceByUserName(userName);
        if (userWorkspaceExists==null) {
        Workspace newWorkSpace = new Workspace(userName);
        return workRepo.save(newWorkSpace);}
        return userWorkspaceExists;
    }
}
