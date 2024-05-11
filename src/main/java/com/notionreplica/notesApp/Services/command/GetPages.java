package com.notionreplica.notesApp.Services.command;

import com.notionreplica.notesApp.repositories.PageRepo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetPages implements CommandInterface {
    PageRepo pageRepo;
    @Override
    public Object execute() {
        return pageRepo.findAll();
    }
}
