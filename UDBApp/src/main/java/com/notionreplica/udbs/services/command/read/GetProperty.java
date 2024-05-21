package com.notionreplica.udbs.services.command.read;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.repository.PropertiesRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GetProperty implements CommandInterface {
    PropertiesRepo propertiesRepo;
    String propertyID;
    @Override
    public Object execute() throws Exception {
        Optional<Properties> properties = propertiesRepo.findById(propertyID);
        if(properties.isEmpty()) {
            throw new Exception(propertyID + " not found");
        }
        return properties.get();
    }
}
