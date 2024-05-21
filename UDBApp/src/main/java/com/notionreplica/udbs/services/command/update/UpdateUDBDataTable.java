package com.notionreplica.udbs.services.command.update;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UpdateUDBDataTable implements CommandInterface {
    UDBDataTableRepo udbDataTableRepo;
    String udbID;
    String title;


    @Override
    public Object execute() throws Exception {
        Optional<UDBDataTable> udbDataTable = udbDataTableRepo.findById(udbID);
        if (udbDataTable.isPresent()) {
            udbDataTable.get().setUdbDataTableTitle(title);
            return udbDataTableRepo.save(udbDataTable.get());
        }
        return "No UDB Table Found";
    }
}
