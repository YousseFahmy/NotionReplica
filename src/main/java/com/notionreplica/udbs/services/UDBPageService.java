package com.notionreplica.udbs.services;

import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.services.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.notionreplica.udbs.services.command.CommandInterface.*;

@Service
public class UDBPageService extends Throwable{
    @Autowired
    private CommandFactory commandFactory;

    public UDBPage createUDBPage(String id, String tableID) throws Exception{
        //THIS ID SHOULD BE GOTTEN FROM NOTES APP (OTHER HALF OF OUR TEAM)
        return (UDBPage) commandFactory.create(CREATE_UDBPAGE, id,tableID).execute();
    }

    public UDBPage getUDBPage(String id) throws Exception{
        return (UDBPage) commandFactory.create(GET_UDBPAGE, id).execute();
    }

    public String deleteUDBPage(String id) throws Exception{
        return (String) commandFactory.create(DELETE_UDBPAGE,id).execute();
    }
}
