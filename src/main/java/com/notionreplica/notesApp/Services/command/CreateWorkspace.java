package com.notionreplica.notesApp.Services.command;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateWorkspace implements CommandInterface {
    WorkspaceRepo workRepo;
    long userId;
    @Override
    public Object execute() {
        Workspace newWorkSpace = new Workspace(userId);
        return workRepo.save(newWorkSpace);
    }
}
