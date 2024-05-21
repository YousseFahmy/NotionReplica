package com.notionreplica.udbs.services.command.read;

import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GetUDBPage implements CommandInterface {
    UDBPageRepo udbPageRepo;
    String pageID;

    @Override
    public Object execute() throws Exception {
        Optional<UDBPage> udbPage = udbPageRepo.findById(pageID);
        if(udbPage.isEmpty())
            throw new Exception(pageID + " not found");
        return udbPage.get();
    }
}
