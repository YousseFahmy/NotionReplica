package com.notionreplica.notesApp.services.command.read;

import com.notionreplica.notesApp.repositories.PageRepo;
import com.notionreplica.notesApp.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetPages implements CommandInterface {
    PageRepo pageRepo;
    String workspaceId;
    @Override
    public Object execute() {
        return pageRepo.findPagesByWorkspaceId(workspaceId);
    }
}
