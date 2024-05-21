package com.notionreplica.udbs.services.command.create;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;

@AllArgsConstructor
public class CreateUDBPage  implements CommandInterface {
    UDBDataTableRepo udbDataTableRepo;
    UDBPageRepo udbPageRepo;
    String pageID;
    String tableID;

    @Override
    public Object execute() throws Exception {
        UDBPage udbPage = new UDBPage();
        udbPage.setPageID(pageID);
        udbPage.setDataTableID(tableID);
        udbPageRepo.save(udbPage);
        UDBDataTable udbDataTable = udbDataTableRepo.findById(tableID).get();
        LinkedHashSet<String> pages = udbDataTable.getUdbPages();
        pages.add(udbPage.getUdbPageID());
        udbDataTable.setUdbPages(pages);
        udbDataTableRepo.save(udbDataTable);
        return udbPage;
    }
}
