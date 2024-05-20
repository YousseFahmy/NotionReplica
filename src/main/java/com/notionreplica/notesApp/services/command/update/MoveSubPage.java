package com.notionreplica.notesApp.services.command.update;

import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class MoveSubPage implements CommandInterface {
    PageRepo pageRepo;
    String pageID;
    String parentPageID;
    String newParentPageID;
    @Override
    public Object execute() throws Exception {
        Optional<Page> parentExists = pageRepo.findById(parentPageID);
        if(parentExists.isPresent()) {
            Page parent = parentExists.get();
            parent.getSubPagesIds().remove(pageID);
        }
        Optional<Page> newParentExists = pageRepo.findById(newParentPageID);
        if(newParentExists.isPresent()) {
            Page newParent = newParentExists.get();
            newParent.getSubPagesIds().add(pageID);
        }
        return null;
    }
}
