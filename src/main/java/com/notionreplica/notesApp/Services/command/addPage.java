package com.notionreplica.notesApp.Services.command;

import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.repositories.WorkspaceRepo;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class addPage implements CommandInterface {
    PageRepo pageRepo;
    WorkspaceRepo workRepo;

    @Override
    public Object execute() {
        return null;
    }
}
