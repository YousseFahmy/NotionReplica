package com.notionreplica.udbs.repository;

import com.notionreplica.udbs.entities.UDBPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UDBPageRepo extends MongoRepository<UDBPage, String> {

}
