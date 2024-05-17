package com.notionreplica.notesApp.services.command.read;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class GetSharedPages implements CommandInterface {
    PageRepo pageRepo;
    Workspace userWorkSpaces;
    @Override
    public Object execute() throws Exception{
        List<String> sharedPagesId = new ArrayList<>();
        for (String pageId: userWorkSpaces.getAccessModifiers().keySet()) {
            if (userWorkSpaces.getAccessModifiers().get(pageId).equals(AccessModifier.SHARED)) {
                sharedPagesId.add(pageId);
            }
        }

        return pageRepo.findAllById(sharedPagesId);
    }
}
