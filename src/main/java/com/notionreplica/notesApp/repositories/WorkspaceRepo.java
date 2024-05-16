package com.notionreplica.notesApp.repositories;

import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepo extends MongoRepository <Workspace, String> {
    Workspace findWorkspaceByUserId(long userId);
    //@Query(value ="{'userId' : ?0}")
    void deleteWorkSpaceByUserId(long userId);

    Workspace deletePagesByWorkSpaceId(String workspaceId);
}
