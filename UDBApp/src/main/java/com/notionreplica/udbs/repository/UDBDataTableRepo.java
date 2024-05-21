package com.notionreplica.udbs.repository;

import com.notionreplica.udbs.entities.UDBDataTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UDBDataTableRepo extends MongoRepository<UDBDataTable, String>  {

}
