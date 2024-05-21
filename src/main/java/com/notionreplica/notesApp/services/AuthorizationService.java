package com.notionreplica.notesApp.services;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.notionreplica.notesApp.services.command.CommandInterface.*;

@Service
public class AuthorizationService {
    @Autowired
    private CommandFactory commandFactory;
    private final ConcurrentMap<String, CompletableFuture<Boolean>> pendingRequests = new ConcurrentHashMap<>();
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private static final String REQUEST_TOPIC = "userRequestTopic";
    private static final String REPLY_TOPIC = "userReplyTopic";
    public CompletableFuture<Boolean> doesUserExistRequest(String username) {
        String correlationId = java.util.UUID.randomUUID().toString();
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        Map<String,Object> requestMessage = new HashMap<>();
        requestMessage.put("username",username);
        requestMessage.put("correlationId",correlationId);
        kafkaTemplate.send(REQUEST_TOPIC, requestMessage);
        return future;
    }
    @KafkaListener(topics = REPLY_TOPIC, groupId = "notesServiceGroup")
    public void listenForReplies(ConsumerRecord<String, Object> record) {
        Map<String,Object> message = (Map<String, Object>) record.value();
        String correlationId = (String)message.get("correlationId");
        String username = (String)message.get("username");
        boolean userExists =(boolean)message.get("userExists");
        CompletableFuture<Boolean> future = pendingRequests.remove(correlationId);
        if (future != null) {
            future.complete(userExists);
        }
    }

    public  boolean isPageOwner(Workspace userWorkspace, String pageId) throws Exception{
        return (boolean) commandFactory.create(IS_PAGE_OWNER,userWorkspace,pageId).execute();
    }

    public Workspace isWorkSpaceOwner(String username,String workspaceId) throws Exception{
        return (Workspace) commandFactory.create(GET_WORKSPACE,username,workspaceId).execute();
    }

    public boolean isRequesterAuthorized(Workspace userWorkspace, String requesterId) throws Exception{
        return (boolean) commandFactory.create(IS_REQUESTER_AUTHORIZED,userWorkspace,requesterId).execute();
    }
    public boolean isUserLoggedIn(Workspace userWorkspace, String requesterId) throws Exception{
        return (boolean) commandFactory.create(IS_REQUESTER_AUTHORIZED,userWorkspace,requesterId).execute();
    }

}
