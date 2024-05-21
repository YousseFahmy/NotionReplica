package com.notionreplica.udbs.services.command.create;

import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUDBPage  implements CommandInterface {
    UDBPageRepo udbPageRepo;
    String pageID;
    String tableID;

    @Override
    public Object execute() throws Exception {
        UDBPage udbPage = new UDBPage();
        udbPage.setPageID(pageID);
        udbPage.setDataTableID(tableID);
        return udbPageRepo.save(udbPage);
    }
}
