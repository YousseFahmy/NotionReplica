package com.notionreplica.notesApp.services;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import com.notionreplica.notesApp.services.command.CommandInterface;
import com.notionreplica.notesApp.entities.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.notionreplica.notesApp.services.command.CommandInterface.DELETE_WORKSPACE;
import static com.notionreplica.notesApp.services.command.CommandInterface.GET_WORKSPACE;

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

    public Page getPage(String pageId,String workspaceId) throws Exception{
            return (Page) CommandFactory.create(CommandInterface.GET_PAGE,pageId,workspaceId).execute();
    }

    public String deletePagesByWorkSpaceId(String workspaceId)throws Exception{
            return (String) CommandFactory.create(CommandInterface.DELETE_PAGES,workspaceId).execute();
    }
    public String deletePage(String pageId,String workspaceId)throws Exception{
        return (String) CommandFactory.create(CommandInterface.DELETE_PAGE,pageId,workspaceId).execute();
    }

    public List<Page> getSharedPages(Workspace userWorkspace) throws Exception{
        return (List<Page>) CommandFactory.create(CommandInterface.GET_SHARED_PAGES,userWorkspace).execute();
    }

    public Page updatePageTitle(String pageId, String pageTitle) throws Exception {

        return (Page) CommandFactory.create(CommandInterface.UPDATE_PAGE_TITLE, pageId,pageTitle).execute();
    }

    public Page updatePageBackground(String pageId, long backgroundId) throws Exception {

        return (Page) CommandFactory.create(CommandInterface.UPDATE_PAGE_BACKGROUND, pageId,backgroundId).execute();
    }
    public Page updatePageIcon(String pageId, long IconId) throws Exception {

        return (Page) CommandFactory.create(CommandInterface.UPDATE_PAGE_ICON, pageId,IconId).execute();
    }

}
