package com.notionreplica.notesApp.repositories;

import com.notionreplica.notesApp.entities.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepo extends MongoRepository <Page, String> {
    @Query(value = "{ 'workspaceId' : ?0 }", fields = "{ 'pageTitle' : 1}")
    List<Object> findPagesByWorkspaceId(String workspaceId);
}
