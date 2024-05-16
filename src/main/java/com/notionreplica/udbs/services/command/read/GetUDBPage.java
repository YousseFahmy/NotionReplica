package com.notionreplica.udbs.services.command.read;

import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUDBPage implements CommandInterface {
    UDBPageRepo udbPageRepo;
    String pageID;

    @Override
    public Object execute() throws Exception {
        UDBPage udbPage = udbPageRepo.findUDBPageByUdbPageID(pageID);
        if(udbPage == null)
            throw new Exception(pageID + " not found");
        return udbPage;
    }
}
