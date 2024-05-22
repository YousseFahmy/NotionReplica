package com.notionreplica.notesApp.repositories;

import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceRepo extends MongoRepository <Workspace, String> {
    Workspace findWorkspaceByUserName(String userName);
    //@Query(value ="{'userId' : ?0}")
    void deleteWorkSpaceByUserName(String userName);

    Workspace deletePagesByWorkSpaceId(String workspaceId);
}
