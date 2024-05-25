package com.notionreplica.notesApp.services.command.update;

import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.exceptions.PageNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;
@AllArgsConstructor
public class DeleteUDB implements CommandInterface {
    PageRepo pageRepo;
    String pageId;
    String UDBId;

    @Override
    public Object execute() throws Exception {
        Optional<Page> userPageExists = pageRepo.findById(pageId);
        if(!userPageExists.isPresent()) {
            throw new PageNotFoundException("the page id " + pageId +"doesnt exist");
        }
        Page userPage = userPageExists.get();
        userPage.getUDBIds().remove(UDBId);
        return pageRepo.save(userPage);
    }}
