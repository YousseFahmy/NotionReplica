package com.notionreplica.udbs.services.command.read;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GetUDBDataTable implements CommandInterface {
    UDBDataTableRepo udbDataTableRepo;
    String tableID;

    @Override
    public Object execute() throws Exception {
        Optional<UDBDataTable> udbDataTable = udbDataTableRepo.findById(tableID);
		if(udbDataTable.isEmpty())
			throw new Exception(tableID + " not found");
		return udbDataTable;

    }
}
