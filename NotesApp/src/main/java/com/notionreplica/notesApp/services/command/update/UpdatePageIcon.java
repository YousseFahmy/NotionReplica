package com.notionreplica.notesApp.services.command.update;

import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.exceptions.PageNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UpdatePageIcon implements CommandInterface {
    PageRepo pageRepo;
    String pageId;
    String IconURL;
    @Override
    public Object execute() throws Exception {
        Optional<Page> userPageExists = pageRepo.findById(pageId);
        if(!userPageExists.isPresent()) {
            throw new PageNotFoundException("the page id " + pageId +"doesnt exist");
        }
        Page userPage = userPageExists.get();
        userPage.setIconURL(IconURL);
        return pageRepo.save(userPage);
    }
}
