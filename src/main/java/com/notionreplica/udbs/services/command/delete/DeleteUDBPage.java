package com.notionreplica.udbs.services.command.delete;

import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteUDBPage implements CommandInterface {
    UDBPageRepo udbPageRepo;
    String udbPageID;

    @Override
    public Object execute() throws Exception {
        UDBPage udbPage = udbPageRepo.findUDBPageByUdbPageID(udbPageID);
        udbPageRepo.delete(udbPage);
        return "Successfully deleted";
    }
}
