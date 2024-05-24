package com.notionreplica.notesApp.services;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.ContentBlock;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import com.notionreplica.notesApp.services.command.CommandInterface;
import com.notionreplica.notesApp.entities.Page;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class NotesService{
    @Autowired
    private CommandFactory commandFactory;

    public Page createPage(String workspaceId , AccessModifier accessModifier,String parent) throws Exception {
            return (Page) commandFactory.create(CommandInterface.CREATE_PAGE,workspaceId,accessModifier,parent).execute();
    }

    public List<Object> getPages(String workspaceId) throws Exception {
            return (List<Object>) commandFactory.create(CommandInterface.GET_PAGES,workspaceId).execute();
    }

    public Page getPage(String pageId,String workspaceId) throws Exception{
            return (Page) commandFactory.create(CommandInterface.GET_PAGE,pageId,workspaceId).execute();
    }

    public String deletePagesByWorkSpaceId(String workspaceId)throws Exception{
            return (String) commandFactory.create(CommandInterface.DELETE_PAGES,workspaceId).execute();
    }
    public String deletePage(String pageId,String workspaceId)throws Exception{
        return (String) commandFactory.create(CommandInterface.DELETE_PAGE,pageId,workspaceId).execute();
    }

    public List<Page> getSharedPages(Workspace userWorkspace) throws Exception{
        return (List<Page>) commandFactory.create(CommandInterface.GET_SHARED_PAGES,userWorkspace).execute();
    }

    public Page updatePageTitle(String pageId, String pageTitle) throws Exception {

        return (Page) commandFactory.create(CommandInterface.UPDATE_PAGE_TITLE, pageId,pageTitle).execute();
    }

    public Page updatePageBackground(String pageId, String backgroundURL) throws Exception {

        return (Page) commandFactory.create(CommandInterface.UPDATE_PAGE_BACKGROUND, pageId,backgroundURL).execute();
    }
    public Page updatePageIcon(String pageId, String IconId) throws Exception {

        return (Page) commandFactory.create(CommandInterface.UPDATE_PAGE_ICON, pageId,IconId).execute();
    }
    public Page updatePageContent(String pageId, List<ContentBlock> pageContent) throws Exception {

        return (Page) commandFactory.create(CommandInterface.UPDATE_PAGE_CONTNET, pageId,pageContent).execute();
    }

    public Page moveSubPage(String pageId, String parentPageId, String newParentPageId) throws Exception {
        return  (Page) commandFactory.create(CommandInterface.MOVE_SUBPAGE,pageId,parentPageId,newParentPageId).execute();
    }

    public Workspace changeAccessModifier(Workspace workSpace, AccessModifier accessModifier, String pageId) throws Exception{
        return  (Workspace) commandFactory.create(CommandInterface.CHANGE_ACCESS_MODIFEIR,workSpace,accessModifier,pageId).execute();

    }
    public Page addUDB(String pageId, String udbId) throws Exception{
        return  (Page) commandFactory.create(CommandInterface.ADD_UDB,pageId,udbId).execute();
    }

    public Page removeUDB(String pageId, String udbId) throws Exception{
        return  (Page) commandFactory.create(CommandInterface.DELETE_UDB,pageId,udbId).execute();
    }
}
