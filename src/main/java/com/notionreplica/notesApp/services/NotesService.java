package com.notionreplica.notesApp.services;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.ContentBlock;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import com.notionreplica.notesApp.services.command.CommandInterface;
import com.notionreplica.notesApp.entities.Page;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.notionreplica.notesApp.services.command.CommandInterface.DELETE_WORKSPACE;
import static com.notionreplica.notesApp.services.command.CommandInterface.GET_WORKSPACE;

@Service
public class NotesService{
    @Autowired
    private CommandFactory CommandFactory;

    public Page createPage(String workspaceId , AccessModifier accessModifier,String parent) throws Exception {
            return (Page) CommandFactory.create(CommandInterface.CREATE_PAGE,workspaceId,accessModifier,parent).execute();
    }

    public List<Object> getPages(String workspaceId) throws Exception {
            return (List<Object>) CommandFactory.create(CommandInterface.GET_PAGES,workspaceId).execute();


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

    public Page updatePageBackground(String pageId, String backgroundURL) throws Exception {

        return (Page) CommandFactory.create(CommandInterface.UPDATE_PAGE_BACKGROUND, pageId,backgroundURL).execute();
    }
    public Page updatePageIcon(String pageId, String IconId) throws Exception {

        return (Page) CommandFactory.create(CommandInterface.UPDATE_PAGE_ICON, pageId,IconId).execute();
    }
    public Page updatePageContent(String pageId, List<ContentBlock> pageContent) throws Exception {

        return (Page) CommandFactory.create(CommandInterface.UPDATE_PAGE_CONTNET, pageId,pageContent).execute();
    }

    public Page moveSubPage(String pageId, String parentPageId, String newParentPageId) {
        return  (Page) CommandFactory.create(CommandInterface.MOVE_SUBPAGE,pageId,parentPageId,newParentPageId);
    }

    public Workspace changeAccessModifier(Workspace workSpace, AccessModifier accessModifier, String pageId) {
        return  (Workspace) CommandFactory.create(CommandInterface.CHANGE_ACCESS_MODIFEIR,workSpace,accessModifier,pageId);

    }


}
