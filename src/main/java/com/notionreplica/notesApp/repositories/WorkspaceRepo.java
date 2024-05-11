package com.notionreplica.notesApp.repositories;

import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepo extends MongoRepository <Workspace, String> {
    Workspace findByUserId(long userId);
}
