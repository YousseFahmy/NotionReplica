package com.notionreplica.notesApp.services.command.read;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class GetWorkspace implements CommandInterface {
    WorkspaceRepo workRepo;
    String userName;
    @Override
    public Object execute() throws Exception {
        Workspace userWorkspaceExists = workRepo.findWorkspaceByUserName(userName);
        if(userWorkspaceExists==null) throw new WorkspaceNotFoundException("");
        return userWorkspaceExists;
    }
}
