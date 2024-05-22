package com.notionreplica.notesApp.services;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import com.notionreplica.notesApp.services.command.CommandInterface;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private CommandFactory commandFactory;

    private static final String PAGE_REQUEST_TOPIC = "pageRequestTopic";
    private static final String PAGE_REPLY_TOPIC = "pageReplyTopic";
    private static final String UDB_REQUEST_TOPIC = "udbRequestTopic";
    private static final String UDB_REPLY_TOPIC = "udbReplyTopic";
    private static final String USER_REQUEST_TOPIC = "userRequestTopic";
    private static final String USER_REPLY_TOPIC = "userReplyTopic";
    private final ConcurrentMap<String, CompletableFuture<Boolean>> userPendingRequests = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, CompletableFuture<String>> udbPendingRequests = new ConcurrentHashMap<>();

    public CompletableFuture<Boolean> doesUserExistRequest(String username) {
        String correlationId = java.util.UUID.randomUUID().toString();
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        userPendingRequests.put(correlationId, future);
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
        CompletableFuture<Boolean> future = userPendingRequests.remove(correlationId);
        if (future != null) {
            future.complete(userExists);
        }
    }


    public CompletableFuture<String> createUDB(String title) {
        String correlationId = java.util.UUID.randomUUID().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        udbPendingRequests.put(correlationId, future);
        String requestMessage = String.format("{\"correlationId\":\"%s\", \"title\":\"%s\" }",
                correlationId,title);
        kafkaTemplate.send(UDB_REQUEST_TOPIC, requestMessage);
        return future;
    }
    @KafkaListener(topics = UDB_REPLY_TOPIC, groupId = "notesServiceGroup")
    public void listenForUDBReplies(ConsumerRecord<String, String> record) {
        String message = record.value();
        org.json.JSONObject jsonObject = new org.json.JSONObject(message);
        String correlationId = jsonObject.getString("correlationId");
        String udbId = jsonObject.getString("udbId");
        CompletableFuture<String> future = udbPendingRequests.remove(correlationId);
        if (future != null) {
            future.complete(udbId);
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
        Page newPage =(Page) commandFactory.create(CommandInterface.CREATE_PAGE,workspaceId,accessModifier,pageId).execute();
        String response = String.format("{\"correlationId\":\"%s\", \"pageId\":\"%s\" }",
                correlationId,newPage.getPageId());
        kafkaTemplate.send(PAGE_REPLY_TOPIC, response);
    }
}
