package com.notionreplica.udbs.services.command;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.entities.PropertyType;
import com.notionreplica.udbs.repository.PropertiesRepo;
import com.notionreplica.udbs.repository.UDBPageRepo;
import com.notionreplica.udbs.repository.UDBDataTableRepo;
import com.notionreplica.udbs.services.command.create.CreateProperty;
import com.notionreplica.udbs.services.command.create.CreateUDBDataTable;
import com.notionreplica.udbs.services.command.create.CreateUDBPage;
import com.notionreplica.udbs.services.command.delete.DeleteProperty;
import com.notionreplica.udbs.services.command.delete.DeleteUDBDataTable;
import com.notionreplica.udbs.services.command.delete.DeleteUDBPage;
import com.notionreplica.udbs.services.command.read.GetProperty;
import com.notionreplica.udbs.services.command.read.GetUDBDataTable;
import com.notionreplica.udbs.services.command.read.GetUDBPage;
import com.notionreplica.udbs.services.command.read.GetUDBPages;
import com.notionreplica.udbs.services.command.update.AddPropertyToUDBDataTable;
import com.notionreplica.udbs.services.command.update.RemovePropertyFromUDBDataTable;
import com.notionreplica.udbs.services.command.update.UpdateProperty;
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

            case GET_UDBPAGES:
                return new GetUDBPages(udbDataTableRepo,udbPageRepo, (String) params[0]);

            case UPDATE_UDBDATATABLE:
                return new UpdateUDBDataTable(udbDataTableRepo,(String) params[0], (String) params[1]);

            case ADD_PROPERTYTOUDBDATATABLE:
                return new AddPropertyToUDBDataTable(udbDataTableRepo,propertiesRepo, (String) params[0], (String) params[1]);

            case REMOVE_PROPERTYFROMUDBDATATABLE:
                return new RemovePropertyFromUDBDataTable(udbDataTableRepo,propertiesRepo, (String) params[0], (String) params[1]);

            case DELETE_UDBDATATABLE:
                return new DeleteUDBDataTable(udbDataTableRepo, udbPageRepo, propertiesRepo, (String) params[0]);

//            case ADD_UDBPAGETOTABLE:
//                return null;
//
//            case REMOVE_UDBPAGEFROMTABLE:
//                return null;

            //UDB PAGE STUFF
            case CREATE_UDBPAGE:
                return new CreateUDBPage(udbDataTableRepo, udbPageRepo, (String) params[0], (String) params[1]);

            case GET_UDBPAGE:
                return new GetUDBPage(udbPageRepo,(String) params[0]);


            case DELETE_UDBPAGE:
                return new DeleteUDBPage(udbDataTableRepo, udbPageRepo,(String) params[0]);
            //STUFF

            case CREATE_PROPERTY:
                return new CreateProperty(propertiesRepo, (PropertyType) params[0], (String) params[1]);

            case GET_PROPERTY:
                return new GetProperty(propertiesRepo, (String) params[0]);

            case DELETE_PROPERTY:
                return new DeleteProperty(propertiesRepo, (String) params[0]);

            case UPDATE_PROPERTY:
                return new UpdateProperty(propertiesRepo,(String) params[0], (PropertyType) params[1], (String) params[2]);

        }
        return null;
    }

}
