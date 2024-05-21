package com.notionreplica.notesApp.services.command.delete;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class deleteWorkSpace implements CommandInterface {
    WorkspaceRepo workRepo;
    PageRepo pageRepo;
    String userName;
    @Override
    public Object execute() throws Exception {
        Workspace userWorkspaceExists = workRepo.findWorkspaceByUserName(userName);
        if(userWorkspaceExists ==null){
            throw new WorkspaceNotFoundException("workspace does not exist");
        }
        pageRepo.deleteAllById(userWorkspaceExists.getAccessModifiers().keySet());
        workRepo.deleteWorkSpaceByUserName(userName);
        return "Workspace deleted successfully";
    }
}
