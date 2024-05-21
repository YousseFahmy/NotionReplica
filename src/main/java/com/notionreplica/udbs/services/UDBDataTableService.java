package com.notionreplica.udbs.services;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.services.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import static com.notionreplica.udbs.services.command.CommandInterface.*;

@Service
public class UDBDataTableService extends Throwable{
    @Autowired
    private CommandFactory commandFactory;

    public UDBDataTable createUDBDataTable(String title) throws Exception {
        return (UDBDataTable) commandFactory.create(CREATE_UDBDATATABLE, title).execute();
    }

    public UDBDataTable getUDBDataTable(String ID) throws Exception {
        return  (UDBDataTable) commandFactory.create(GET_UDBDATATABLE,ID).execute();
    }

    public UDBDataTable updateUDBDataTable(String ID, String title) throws Exception {
        return (UDBDataTable) commandFactory.create(UPDATE_UDBDATATABLE, ID, title).execute();
    }

    public UDBDataTable addPropertyToUDBDataTable(String ID, String propertyID) throws Exception {
        return (UDBDataTable) commandFactory.create(ADD_PROPERTYTOUDBDATATABLE, ID, propertyID).execute();
    }

    public String removePropertyFromUDBDataTable(String ID, String propertyID) throws Exception {
        return (String) commandFactory.create(REMOVE_PROPERTYFROMUDBDATATABLE, ID, propertyID).execute();
    }

    public LinkedHashSet<String> getAllUDBPagesInTable(String ID){
        return (LinkedHashSet<String>) commandFactory.create(GET_UDBPAGES, ID);
    }

    public String deleteUDBDataTable(String ID) throws Exception {
        return (String) commandFactory.create(DELETE_UDBDATATABLE, ID).execute();
    }
}
