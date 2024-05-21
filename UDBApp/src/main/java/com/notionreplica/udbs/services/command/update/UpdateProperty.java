package com.notionreplica.udbs.services.command.update;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.entities.PropertyType;
import com.notionreplica.udbs.repository.PropertiesRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UpdateProperty implements CommandInterface {
    PropertiesRepo propertiesRepo;
    String propertyID;
    PropertyType propertyType;
    String title;


    @Override
    public Object execute() throws Exception {
        Optional<Properties> properties = propertiesRepo.findById(propertyID);
        if (properties.isEmpty()) {
            throw new Exception("Property " + propertyID + " not found");
        }
        if(propertyType != null)
            properties.get().setType(propertyType);
        if(title != null)
            properties.get().setTitle(title);

        return properties.get();
    }
}
