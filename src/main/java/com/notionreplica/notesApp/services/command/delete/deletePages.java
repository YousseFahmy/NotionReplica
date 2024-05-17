package com.notionreplica.notesApp.services.command.delete;

import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.exceptions.WorkspaceNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class deletePages implements CommandInterface {
    PageRepo pageRepo;
    WorkspaceRepo workRepo;
    String workspaceId;
    @Override
    public Object execute() throws Exception {
        Optional<Workspace> userWorkspaceExists = workRepo.findById(workspaceId);
        if(!userWorkspaceExists.isPresent()){
            throw new WorkspaceNotFoundException("workspace does not exist");
        }
        Workspace userWorkSpace= userWorkspaceExists.get();

        Set<String> pagesToDelete= userWorkSpace.getAccessModifiers().keySet();
        userWorkSpace.getAccessModifiers().clear();
        workRepo.save(userWorkSpace);
        pageRepo.deleteAllById(pagesToDelete);
        return "pages deleted successfully";
    }
}
