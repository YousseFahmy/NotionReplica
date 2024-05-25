package com.notionreplica.notesApp.services.command.update;

import com.notionreplica.notesApp.entities.ContentBlock;
import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.exceptions.PageNotFoundException;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UpdatePageContent implements CommandInterface {
    PageRepo pageRepo;
    String pageId;
    private List<ContentBlock> newPageContent = new ArrayList<>();
    @Override
    public Object execute() throws Exception {
        Optional<Page> userPageExists = pageRepo.findById(pageId);
        if(!userPageExists.isPresent()) {
            throw new PageNotFoundException("the page id " + pageId +"doesnt exist");
        }
        Page userPage = userPageExists.get();
        userPage.setPageContent(newPageContent);
        return pageRepo.save(userPage);
    }
}
