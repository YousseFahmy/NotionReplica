package com.notionreplica.notion.notes;

import com.notionreplica.notion.notes.entities.Page;
import com.notionreplica.notion.notes.repositories.PageRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = PageRepo.class)
public class NotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}
//	@Bean
//	CommandLineRunner runner(PageRepo pageRepo){
//		return args -> {Page page = new Page(-1,5456546);
//		pageRepo.save(page);
//		};
//
//	}
}
