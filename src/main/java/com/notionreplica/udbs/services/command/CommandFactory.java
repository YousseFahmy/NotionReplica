package com.notionreplica.udbs.services.command;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.repository.PropertiesRepo;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.services.command.create.CreateUDBDataTable;
import com.notionreplica.udbs.services.command.delete.DeleteUDBDataTable;
import com.notionreplica.udbs.services.command.read.GetUDBDataTable;
import com.notionreplica.udbs.services.command.read.GetUDBPage;
import com.notionreplica.udbs.services.command.update.AddPropertyToUDBDataTable;
import com.notionreplica.udbs.services.command.update.RemovePropertyFromUDBDataTable;
import com.notionreplica.udbs.services.command.update.UpdateUDBDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.notionreplica.udbs.services.command.CommandInterface.*;


@Component
public class CommandFactory {
    @Autowired
    private UDBDataTableRepo udbDataTableRepo;

    @Autowired
    private UDBPageRepo udbPageRepo;

    @Autowired
    private PropertiesRepo propertiesRepo;

    public CommandInterface create(int commandCode, Object... params){
        switch (commandCode){
            //UDB DATA TABLE STUFF
            case CREATE_UDBDATATABLE:
                return new CreateUDBDataTable(udbDataTableRepo, (String) params[0]);

            case GET_UDBDATATABLE:
                return new GetUDBDataTable(udbDataTableRepo, (String) params[0]);

            case UPDATE_UDBDATATABLE:
                return new UpdateUDBDataTable(udbDataTableRepo,(String) params[0], (String) params[1]);

            case ADD_PROPERTYTOUDBDATATABLE:
                return new AddPropertyToUDBDataTable(udbDataTableRepo,propertiesRepo, (String) params[0], (String) params[1]);

            case REMOVE_PROPERTYFROMUDBDATATABLE:
                return new RemovePropertyFromUDBDataTable(udbDataTableRepo,propertiesRepo, (String) params[0], (String) params[1]);


            case DELETE_UDBDATATABLE:
                return new DeleteUDBDataTable(udbDataTableRepo, (String) params[0]);

            //UDB PAGE STUFF
            case CREATE_UDBPAGE:
                return null;

            case GET_UDBPAGE:
                return new GetUDBPage(udbPageRepo,(String) params[0]);

            //STUFF
        }
        return null;
    }

}
