package com.notionreplica.searchapp.repositories;

import com.notionreplica.searchapp.dto.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepo extends MongoRepository <Workspace, String> {
    Workspace findWorkspaceByUserName(String userName);
    //@Query(value ="{'userId' : ?0}")
    void deleteWorkSpaceByUserName(String userName);

    Workspace deletePagesByWorkSpaceId(String workspaceId);
}
