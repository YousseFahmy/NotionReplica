package com.notionreplica.notesApp.services.command.delete;

import com.notionreplica.notesApp.entities.AccessModifier;
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
            throw new WorkspaceNotFoundException("The worksspace"+ workspaceId +" doesn't exist");
        }
        Workspace userWorkSpace= userWorkspaceExists.get();

        Set<String> pagesToDelete= userWorkSpace.getAccessModifiers().keySet();
        for(String page : pagesToDelete){
            userWorkSpace.getAccessModifiers().put(page,AccessModifier.DELETED);
        }
        workRepo.save(userWorkSpace);
        return "pages deleted successfully";
    }
}
