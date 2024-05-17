package com.notionreplica.udbs.services.command.read;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUDBDataTable implements CommandInterface {
    UDBDataTableRepo udbDataTableRepo;
    String tableID;

    @Override
    public Object execute() throws Exception {
//        UDBDataTable udbDataTable = udbDataTableRepo.findUDBDataTableByUdbDataTableID(tableID);
//		if(udbDataTable == null)
//			throw new Exception(tableID + " not found");
//		return udbDataTable;
        return null;
    }
}
