package com.notionreplica.udbs.services.command.update;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class RemoveUDBPageFromUDBDataTable implements CommandInterface {
        UDBDataTableRepo udbDataTableRepo;
        UDBPageRepo udbPageRepo;
        String udbTableID;
        String udbPageID;

    @Override
    public Object execute() throws Exception {
        Optional<UDBDataTable> udbDataTable = udbDataTableRepo.findById(udbTableID);
        if (udbDataTable.isPresent()) {
            Optional<UDBPage> page = udbPageRepo.findById(udbPageID);
            if (page.isPresent() && udbDataTable.get().getUdbPages().contains(page.get())){
                udbDataTable.get().getUdbPages().remove(page.get());
                return udbDataTableRepo.save(udbDataTable.get());
            }
            return "UDB Page not found";
        }
        return "No UDB Table Found";
    }
}
