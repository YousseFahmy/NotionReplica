package com.notionreplica.udbs.services.command.delete;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteUDBPage implements CommandInterface {
    UDBDataTableRepo tableRepo;
    UDBPageRepo udbPageRepo;
    String udbPageID;

    @Override
    public Object execute() throws Exception {
        String table = udbPageRepo.findById(udbPageID).get().getDataTableID();
        UDBDataTable udbDataTable= tableRepo.findById(table).get();
        udbDataTable.getUdbPages().remove(udbPageID);
        tableRepo.save(udbDataTable);
        udbPageRepo.deleteById(udbPageID);
        return "Successfully deleted";
    }
}
