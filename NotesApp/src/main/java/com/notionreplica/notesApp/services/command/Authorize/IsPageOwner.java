package com.notionreplica.notesApp.services.command.Authorize;

import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IsPageOwner implements CommandInterface {
    Workspace userWorkspace;
    String pageId ;

    @Override
    public Object execute() throws Exception {
        return userWorkspace.getAccessModifiers().keySet().contains(pageId);
    }
}

