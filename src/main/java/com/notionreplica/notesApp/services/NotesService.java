package com.notionreplica.notesApp.services;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.services.command.CommandFactory;
import com.notionreplica.notesApp.services.command.CommandInterface;
import com.notionreplica.notesApp.entities.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotesService{
    @Autowired
    private CommandFactory CommandFactory;

    public Page createPage(String workspaceId , AccessModifier accessModifier,String parent){
        try{
            return (Page) CommandFactory.create(CommandInterface.CREATE_PAGE,workspaceId,accessModifier,parent).execute();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<Object> getPages(String workspaceId){
        try {
            return (List<Object>) CommandFactory.create(CommandInterface.GET_PAGES,workspaceId).execute();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
