package com.notionreplica.notesApp.services.command.update;

import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.exceptions.PageNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UpdatePageTitle implements CommandInterface {
    PageRepo pageRepo;
    String pageId;
    String pageTitle;

    @Override
    public Object execute() throws Exception {
        Optional<Page> userPageExists = pageRepo.findById(pageId);
        if(!userPageExists.isPresent()) {
            throw new PageNotFoundException("");
        }
        Page userPage = userPageExists.get();
        pageTitle =pageTitle.equals("") ? "untitled" : pageTitle;
        userPage.setPageTitle(pageTitle);
        return pageRepo.save(userPage);
    }
}