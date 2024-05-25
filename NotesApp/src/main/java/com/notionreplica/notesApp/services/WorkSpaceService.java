package com.notionreplica.notesApp.services;


import com.notionreplica.notesApp.services.command.CommandFactory;
import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.notionreplica.notesApp.services.command.CommandInterface.*;

@Service
public class WorkSpaceService {
    @Autowired
    private CommandFactory CommandFactory;
    public Workspace createWorkSpace(String userName) throws Exception{
            return (Workspace) CommandFactory.create(CREATE_WORKSPACE,userName).execute();
    }
    public Workspace getWorkSpace(String userName) throws Exception{
            return (Workspace) CommandFactory.create(GET_WORKSPACE,userName).execute();
    }
    public String deleteWorkSpace(String userName) throws Exception {
            return (String) CommandFactory.create(DELETE_WORKSPACE,userName).execute();
    }

    public Workspace addUserToWorkspace(String userName, String newUserName) throws Exception {
        return (Workspace) CommandFactory.create(ADD_USER_TO_WORKSPACE,userName,newUserName).execute();
    }
    public Workspace removeUserFromWorkspace(String userName, String newUserName) throws Exception {
        return (Workspace) CommandFactory.create(REMOVE_USER_FROM_WORKSPACE,userName,newUserName).execute();
    }
}
