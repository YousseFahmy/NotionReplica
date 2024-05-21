package com.notionreplica.udbs.services.command.create;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.entities.PropertyType;
import com.notionreplica.udbs.repository.PropertiesRepo;
import com.notionreplica.udbs.services.command.CommandInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateProperty implements CommandInterface {
    PropertiesRepo propertiesRepo;
    PropertyType type;
    String title;

    @Override
    public Object execute() throws Exception {
        Properties properties = new Properties();
        properties.setType(type);
        properties.setTitle(title);
        return propertiesRepo.save(properties);

    }
}
