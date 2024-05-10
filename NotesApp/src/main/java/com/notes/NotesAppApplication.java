package com.notes;

import com.notes.entities.Page;
import com.notes.entities.Workspace;
import com.notes.repositories.PageRepo;
import com.notes.repositories.WorkspaceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class NotesAppApplication {
@Autowired
PageRepo mostafa;
@Autowired
WorkspaceRepo zeina;
  private final static Logger log = LoggerFactory.getLogger(NotesAppApplication.class);
  
  public static void main(String[] args) {
    SpringApplication.run(NotesAppApplication.class, args);
  }

}
