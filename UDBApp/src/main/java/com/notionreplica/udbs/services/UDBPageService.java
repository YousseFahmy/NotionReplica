package com.notionreplica.udbs.services;

import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.services.command.CommandFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.notionreplica.udbs.services.command.CommandInterface.*;

@Service
public class UDBPageService extends Throwable{
    @Autowired
    private CommandFactory commandFactory;

    private final ConcurrentMap<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private static final String REQUEST_TOPIC = "pageRequestTopic";
    private static final String REPLY_TOPIC = "pageReplyTopic";

    public CompletableFuture<String> createPageRequest(String username,String workspaceId,String pageID) {
        String correlationId = java.util.UUID.randomUUID().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        String requestMessage = String.format("{\"correlationId\":\"%s\", \"username\":\"%s\" , \"workspaceId\":\"%s\",  \"pageId\":\"%s\" }",
                correlationId, username,workspaceId,pageID);
        kafkaTemplate.send(REQUEST_TOPIC, requestMessage);

        return future;
    }


    @KafkaListener(topics = REPLY_TOPIC, groupId = "udbPageServiceGroup")
    public void listenForReplies(ConsumerRecord<String, String> record) {
        String message = record.value();
        org.json.JSONObject jsonObject = new org.json.JSONObject(message);
        String correlationId = jsonObject.getString("correlationId");
        String pageId = jsonObject.getString("pageId");
        CompletableFuture<String> future = pendingRequests.remove(correlationId);
        if (future != null) {
            future.complete(pageId);
        }
    }

    public UDBPage createUDBPage(String id, String tableID) throws Exception{
        return (UDBPage) commandFactory.create(CREATE_UDBPAGE, id,tableID).execute();
    }

    public UDBPage getUDBPage(String id) throws Exception{
        return (UDBPage) commandFactory.create(GET_UDBPAGE, id).execute();
    }

    public String deleteUDBPage(String id) throws Exception{
        return (String) commandFactory.create(DELETE_UDBPAGE,id).execute();
    }
}
