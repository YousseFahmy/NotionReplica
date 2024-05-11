package com.notionreplica.notesApp.Services;

import com.notionreplica.notesApp.Services.command.CommandFactory;
import com.notionreplica.notesApp.Services.command.CommandInterface;
import com.notionreplica.notesApp.entities.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotesService{
    @Autowired
    private CommandFactory CommandFactory;
    public Page addPage(){
        return (Page) CommandFactory.create(CommandInterface.ADD_PAGE).execute();
    }
    public List<Page> getPages(){
        return (List<Page>) CommandFactory.create(CommandInterface.GET_PAGES).execute();
    }
}
