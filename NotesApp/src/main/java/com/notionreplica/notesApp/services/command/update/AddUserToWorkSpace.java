package com.notionreplica.notesApp.services.command.update;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;
//todo remove user from workspace
@AllArgsConstructor
public class AddUserToWorkSpace implements CommandInterface {
    WorkspaceRepo workspaceRepo;
    String userName;
    String newUserName;

    @Override
    public Object execute() throws Exception {
        Workspace userWorkspace = workspaceRepo.findWorkspaceByUserName(userName);
        if(userWorkspace == null) throw new WorkspaceNotFoundException("The user id doesn't have a workspace");
        userWorkspace.getUsersWithAccess().add(newUserName);
        return workspaceRepo.save(userWorkspace);
    }
}