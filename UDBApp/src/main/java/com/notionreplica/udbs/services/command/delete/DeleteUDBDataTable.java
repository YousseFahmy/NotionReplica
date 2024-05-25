package com.notionreplica.udbs.services.command.delete;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.repository.PropertiesRepo;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;

@AllArgsConstructor
public class DeleteUDBDataTable implements CommandInterface {
    UDBDataTableRepo udbDataTableRepo;
    UDBPageRepo udbPageRepo;
    PropertiesRepo propertiesRepo;
    String tableID;

    @Override
    public Object execute() throws Exception {
        Optional<UDBDataTable> udbDataTable = udbDataTableRepo.findById(tableID);
        if (udbDataTable.isEmpty()) {
            return null;
        }
        LinkedHashSet<String> udbPagesToDelete = udbDataTable.get().getUdbPages();
        LinkedHashSet<Properties> propertiesInTable = udbDataTable.get().getProperties();
        ArrayList<String> propertiesToDelete = new ArrayList<>();
        for (Properties prop : propertiesInTable) {
            propertiesToDelete.add(prop.getPropertyID());
        }
        udbPageRepo.deleteAllById(udbPagesToDelete);
        propertiesRepo.deleteAllById(propertiesToDelete);
        udbDataTableRepo.delete(udbDataTable.get());
        return "Successfully deleted";
    }
}
