package com.notionreplica.notesApp.services;
import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.notionreplica.notesApp.services.command.CommandInterface.*;

@Service
public class AuthorizationService {
    @Autowired
    private CommandFactory CommandFactory;
    private final ConcurrentMap<String, CompletableFuture<Boolean>> pendingRequests = new ConcurrentHashMap<>();
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private static final String REQUEST_TOPIC = "userRequestTopic";
    private static final String REPLY_TOPIC = "userReplyTopic";
    public CompletableFuture<Boolean> doesUserExistRequest(UUID userId) {
        String correlationId = java.util.UUID.randomUUID().toString();
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        Map<String,Object> requestMessage = new HashMap<>();
        requestMessage.put("userId",userId);
        requestMessage.put("correlationId",correlationId);
        kafkaTemplate.send(REQUEST_TOPIC, requestMessage);
        return future;
    }
    @KafkaListener(topics = REPLY_TOPIC, groupId = "notesServiceGroup")
    public void listenForReplies(ConsumerRecord<String, Object> record) {
        Map<String,Object> message = (Map<String, Object>) record.value();
        String correlationId = (String)message.get("correlationId");
        String userId = (String)message.get("userId");
        boolean userExists =(boolean)message.get("userExists");
        CompletableFuture<Boolean> future = pendingRequests.remove(correlationId);
        if (future != null) {
            future.complete(userExists);
        }
    }

    public  boolean isPageOwner(Workspace userWorkspace, String pageId) throws Exception{
        return (boolean) CommandFactory.create(IS_PAGE_OWNER,userWorkspace,pageId).execute();
    }

    public Workspace isWorkSpaceOwner(UUID userId,String workspaceId) throws Exception{
        return (Workspace) CommandFactory.create(GET_WORKSPACE,userId,workspaceId).execute();
    }

    public boolean isRequesterAuthorized(Workspace userWorkspace, UUID requesterId) throws Exception{
        return (boolean) CommandFactory.create(IS_REQUESTER_AUTHORIZED,userWorkspace,requesterId).execute();
    }
    public boolean isUserLoggedIn(Workspace userWorkspace, UUID requesterId) throws Exception{
        return (boolean) CommandFactory.create(IS_REQUESTER_AUTHORIZED,userWorkspace,requesterId).execute();
    }

}
