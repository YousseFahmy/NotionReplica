package com.notionreplica.udbs.services;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.services.command.CommandFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.notionreplica.udbs.services.command.CommandInterface.*;

@Service
public class UDBDataTableService extends Throwable{
    @Autowired
    private CommandFactory commandFactory;

    private final ConcurrentMap<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private static final String REQUEST_TOPIC = "udbRequestTopic";
    private static final String REPLY_TOPIC = "udbReplyTopic";
    Logger log = LoggerFactory.getLogger(UDBDataTableService.class);

    @KafkaListener(topics = REQUEST_TOPIC, groupId = "udbTableServiceGroup")
    public void listenForRequests(ConsumerRecord<String, String> record) throws Exception {
        String message = record.value();
        org.json.JSONObject jsonObject = new org.json.JSONObject(message);
        String correlationId = jsonObject.getString("correlationId");
        String title = jsonObject.getString("title");
        UDBDataTable newUDBDataTable = createUDBDataTable(title);
        String udbId = newUDBDataTable.getUdbDataTableID();
        String response = String.format("{\"correlationId\":\"%s\", \"udbId\":\"%s\" }",
                correlationId,udbId);
        log.info("Creating new UDBDataTable with ID {} and title {}", udbId, title);
        kafkaTemplate.send(REPLY_TOPIC, response);

    }


    public UDBDataTable createUDBDataTable(String title) throws Exception {
        return (UDBDataTable) commandFactory.create(CREATE_UDBDATATABLE, title).execute();
    }

    public UDBDataTable getUDBDataTable(String ID) throws Exception {
        return  (UDBDataTable) commandFactory.create(GET_UDBDATATABLE,ID).execute();
    }

    public UDBDataTable updateUDBDataTable(String ID, String title) throws Exception {
        return (UDBDataTable) commandFactory.create(UPDATE_UDBDATATABLE, ID, title).execute();
    }

    public Object addPropertyToUDBDataTable(String ID, String propertyID) throws Exception {
        return (Object) commandFactory.create(ADD_PROPERTYTOUDBDATATABLE, ID, propertyID).execute();
    }

    public String removePropertyFromUDBDataTable(String ID, String propertyID) throws Exception {
        return (String) commandFactory.create(REMOVE_PROPERTYFROMUDBDATATABLE, ID, propertyID).execute();
    }

    public Object addUDBPageToUDBDataTable(String ID, String pageID) throws Exception {
        return (Object) commandFactory.create(ADD_UDBPAGETOTABLE, ID, pageID).execute();
    }

    public String removeUDBPageFromUDBDataTable(String ID, String pageID) throws Exception {
        return (String) commandFactory.create(REMOVE_UDBPAGEFROMTABLE, ID, pageID).execute();
    }


    public LinkedHashSet<String> getAllUDBPagesInTable(String ID) throws Exception{
        return (LinkedHashSet<String>) commandFactory.create(GET_UDBPAGES, ID).execute();
    }

    public String deleteUDBDataTable(String ID) throws Exception {
        return (String) commandFactory.create(DELETE_UDBDATATABLE, ID).execute();
    }
}
