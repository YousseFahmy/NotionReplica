package com.notionreplica.udbs.services.command.read;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GetUDBPages implements CommandInterface {
    UDBDataTableRepo udbDataTableRepo;
    UDBPageRepo udbPageRepo;
    String udbTableID;

    @Override
    public Object execute() throws Exception {
        Optional<UDBDataTable> udbDataTable = udbDataTableRepo.findById(udbTableID);
        if (udbDataTable.isPresent()) {
            return udbDataTable.get().getUdbPages();
        }
        return "No UDB Table Found";
    }
}
