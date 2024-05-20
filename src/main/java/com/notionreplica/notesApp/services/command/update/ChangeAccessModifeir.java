package com.notionreplica.notesApp.services.command.update;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeAccessModifeir implements CommandInterface {
    WorkspaceRepo workspaceRepo;
    Workspace userWorkspace;
    AccessModifier accessModifier;
    String pageId;

    @Override
    public Object execute() throws Exception {
        userWorkspace.getAccessModifiers().put(pageId,accessModifier);
        return workspaceRepo.save(userWorkspace);
    }
}
