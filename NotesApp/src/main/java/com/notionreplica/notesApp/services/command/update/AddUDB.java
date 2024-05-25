package com.notionreplica.notesApp.services.command.update;

import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.exceptions.PageNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class AddUDB implements CommandInterface {
    PageRepo pageRepo;
    String pageId;
    String UDBId;

    @Override
    public Object execute() throws Exception {
        Optional<Page> userPageExists = pageRepo.findById(pageId);
        if(!userPageExists.isPresent()) {
            throw new PageNotFoundException("");
        }
        Page userPage = userPageExists.get();
        Set<String> newUDBIds =  userPage.getUDBIds();
        newUDBIds.add(UDBId);
        userPage.setUDBIds(newUDBIds);
        return pageRepo.save(userPage);
    }
}
