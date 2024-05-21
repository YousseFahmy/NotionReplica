package com.notionreplica.notesApp.services;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.ContentBlock;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import com.notionreplica.notesApp.services.command.CommandInterface;
import com.notionreplica.notesApp.entities.Page;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class NotesService{
    @Autowired
    private CommandFactory commandFactory;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
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

    public Page moveSubPage(String pageId, String parentPageId, String newParentPageId) {
        return  (Page) commandFactory.create(CommandInterface.MOVE_SUBPAGE,pageId,parentPageId,newParentPageId);
    }

    public Workspace changeAccessModifier(Workspace workSpace, AccessModifier accessModifier, String pageId) {
        return  (Workspace) commandFactory.create(CommandInterface.CHANGE_ACCESS_MODIFEIR,workSpace,accessModifier,pageId);

    }

    private static final String PAGE_REPLY_TOPIC = "pageReplyTopic";
    private static final String PAGE_REQUEST_TOPIC = "pageRequestTopic";
    private final ConcurrentMap<String, CompletableFuture<Boolean>> pendingRequests = new ConcurrentHashMap<>();
    private static final String USER_REQUEST_TOPIC = "userRequestTopic";
    private static final String USER_REPLY_TOPIC = "userReplyTopic";
    public CompletableFuture<Boolean> doesUserExistRequest(String username) {
        String correlationId = java.util.UUID.randomUUID().toString();
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        String requestMessage = String.format("{\"correlationId\":\"%s\", \"username\":\"%s\" }",
                correlationId,username);
        kafkaTemplate.send(USER_REQUEST_TOPIC, requestMessage);
        return future;
    }
    @KafkaListener(topics = USER_REPLY_TOPIC, groupId = "notesServiceGroup")
    public void listenForReplies(ConsumerRecord<String, String> record) {
        String message = record.value();

        org.json.JSONObject jsonObject = new org.json.JSONObject(message);

        String correlationId = jsonObject.getString("correlationId");
        String username = jsonObject.getString("username");
        boolean userExists =Boolean.parseBoolean(jsonObject.getString("userExists"));
        CompletableFuture<Boolean> future = pendingRequests.remove(correlationId);
        if (future != null) {
            future.complete(userExists);
        }
    }
    @KafkaListener(topics = PAGE_REQUEST_TOPIC, groupId = "notesServiceGroup")
    public void listenForRequests(ConsumerRecord<String, String> record) throws Exception {
        String message = record.value();
        org.json.JSONObject jsonObject = new org.json.JSONObject(message);
        String correlationId = jsonObject.getString("correlationId");
        String username = jsonObject.getString("username");
        String workspaceId = jsonObject.getString("workspaceId");
        String pageId = jsonObject.getString("pageId");
        Workspace userWorkspace = (Workspace)commandFactory.create(CommandInterface.IS_WORKSPACE_OWNER,username,workspaceId).execute();
        boolean isPageOwner = (boolean)commandFactory.create(CommandInterface.IS_PAGE_OWNER,userWorkspace,pageId).execute();
        if (!isPageOwner) {
            throw new AccessDeniedException("You do not have permission to access this page");
        }
        AccessModifier accessModifier = userWorkspace.getAccessModifiers().get(pageId);
        Page newPage = createPage(workspaceId, accessModifier, pageId);
        String response = String.format("{\"correlationId\":\"%s\", \"pageId\":\"%s\" }",
                correlationId,newPage.getPageId());
        kafkaTemplate.send(PAGE_REPLY_TOPIC, response);
    }


}
