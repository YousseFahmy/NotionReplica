package com.notes.repositories;

import com.notes.entities.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PageRepo extends MongoRepository <Page, UUID> {
}
