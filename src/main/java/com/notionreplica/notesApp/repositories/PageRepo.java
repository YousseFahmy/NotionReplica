package com.notionreplica.notesApp.repositories;

import com.notionreplica.notesApp.entities.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepo extends MongoRepository <Page, String> {
}
