package com.notes.repositories;

import com.notes.entities.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkspaceRepo extends MongoRepository <Workspace, UUID> {
}
