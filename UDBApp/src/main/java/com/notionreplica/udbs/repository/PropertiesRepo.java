package com.notionreplica.udbs.repository;

import com.notionreplica.udbs.entities.Properties;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesRepo  extends MongoRepository<Properties, String> {
}
