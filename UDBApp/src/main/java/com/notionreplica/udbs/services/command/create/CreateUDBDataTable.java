package com.notionreplica.udbs.services.command.create;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUDBDataTable implements CommandInterface {
    UDBDataTableRepo udbDataTableRepo;
    String title;
    @Override
    public Object execute(){
        UDBDataTable udbDataTable = new UDBDataTable();
        udbDataTable.setUdbDataTableTitle(title);
        return udbDataTableRepo.save(udbDataTable);
    }
}
