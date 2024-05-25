package com.notionreplica.notesApp.services.command.update;

import ch.qos.logback.core.joran.spi.ActionException;
import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.Set;

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
            if(parent.getSubPagesIds().contains(pageID)) throw  new ActionException("the parent page doesnt contain this subpage");
            Set<String> newSubPagesIds = parent.getSubPagesIds();
            newSubPagesIds.remove(pageID);
            parent.setSubPagesIds(newSubPagesIds);
            pageRepo.save(parent);
        }
        Optional<Page> newParentExists = pageRepo.findById(newParentPageID);
        if(newParentExists.isPresent()) {
            Page newParent = newParentExists.get();
            Set<String> newSubPagesIds = newParent.getSubPagesIds();
            newSubPagesIds.add(pageID);
            newParent.setSubPagesIds(newSubPagesIds);
            pageRepo.save(newParent);
        }
        return newParentExists.get();
    }
}
