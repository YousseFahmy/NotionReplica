package com.notionreplica.notesApp.services;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.services.command.CommandFactory;
import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.notionreplica.notesApp.services.command.CommandInterface.*;

@Service
public class WorkSpaceService {
    @Autowired
    private CommandFactory CommandFactory;
    public Workspace createWorkSpace(UUID userId) throws Exception{
            return (Workspace) CommandFactory.create(CREATE_WORKSPACE,userId).execute();
    }
    public Workspace getWorkSpace(UUID userId) throws Exception{
            return (Workspace) CommandFactory.create(GET_WORKSPACE,userId).execute();
    }
    public String deleteWorkSpace(UUID userId) throws Exception {
            return (String) CommandFactory.create(DELETE_WORKSPACE,userId).execute();
    }

    public Workspace addUserToWorkspace(UUID userId, UUID newUserId) throws Exception {
        return (Workspace) CommandFactory.create(ADD_USER_TO_WORKSPACE,userId,newUserId).execute();
    }
    public Workspace removeUserFromWorkspace(UUID userId, UUID newUserId) throws Exception {
        return (Workspace) CommandFactory.create(REMOVE_USER_FROM_WORKSPACE,userId,newUserId).execute();
    }
}
