package com.notionreplica.udbs.services.command.update;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.repository.PropertiesRepo;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class AddPropertyToUDBDataTable implements CommandInterface {
    //NOT TESTED
    //IT JUST WORKS ~SOURCE: TRUST ME BRO

    UDBDataTableRepo udbDataTableRepo;
    PropertiesRepo propertiesRepo;
    String udbID;
    String propertyID;
    @Override
    public Object execute() throws Exception {
        Optional<UDBDataTable> udbDataTable = udbDataTableRepo.findById(udbID);
        if (udbDataTable.isPresent()) {
            Optional<Properties> prop = propertiesRepo.findById(propertyID);
            if (prop.isPresent()){
                udbDataTable.get().getProperties().add(prop.get());
                return udbDataTableRepo.save(udbDataTable.get());
            }
            return "Property not found";
        }
        return "No UDB Table Found";
    }
}
