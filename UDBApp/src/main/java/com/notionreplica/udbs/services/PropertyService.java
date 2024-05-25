package com.notionreplica.udbs.services;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.entities.PropertyType;
import com.notionreplica.udbs.services.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.notionreplica.udbs.services.command.CommandInterface.*;

@Service
public class PropertyService {
    @Autowired
    private CommandFactory commandFactory;

    public Properties createProperty(PropertyType propertyType, String title) throws Exception {
        return (Properties) commandFactory.create(CREATE_PROPERTY, propertyType, title).execute();
    }

    public Properties getProperty(String id) throws Exception {
        return (Properties) commandFactory.create(GET_PROPERTY, id).execute();
    }

    public Properties updateProperty(String id, PropertyType propertyType, String title) throws Exception {
        return (Properties) commandFactory.create(UPDATE_PROPERTY, id, propertyType, title).execute();
    }

    public String deleteProperty(String id) throws Exception {
        return (String) commandFactory.create(DELETE_PROPERTY, id).execute();
    }


}
