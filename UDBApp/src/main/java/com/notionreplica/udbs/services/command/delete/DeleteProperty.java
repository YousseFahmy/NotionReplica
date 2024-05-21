package com.notionreplica.udbs.services.command.delete;

import com.notionreplica.udbs.repository.PropertiesRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteProperty implements CommandInterface {
    PropertiesRepo propertiesRepo;
    String propertyID;


    @Override
    public Object execute() throws Exception {
        propertiesRepo.deleteById(propertyID);
        return "Successfully deleted";
    }
}
