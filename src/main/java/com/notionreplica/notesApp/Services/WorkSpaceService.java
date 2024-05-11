package com.notionreplica.notesApp.Services;

import com.notionreplica.notesApp.Services.command.CommandFactory;
import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.notionreplica.notesApp.Services.command.CommandInterface.CREATE_WORKSPACE;

@Service
public class WorkSpaceService {
    @Autowired
    private CommandFactory CommandFactory;
    public Workspace createWorkSpace(long userId){
            return (Workspace) CommandFactory.create(CREATE_WORKSPACE,userId).execute();
    }
}
