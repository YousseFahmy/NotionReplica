package com.notionreplica.udbs.services.command.delete;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteUDBDataTable implements CommandInterface {
    UDBDataTableRepo udbDataTableRepo;
    String tableID;

    @Override
    public Object execute() throws Exception {
        UDBDataTable udbDataTable = udbDataTableRepo.findUDBDataTableByUdbDataTableID(tableID);
        udbDataTableRepo.delete(udbDataTable);
        return "Successfully deleted";
    }
}
