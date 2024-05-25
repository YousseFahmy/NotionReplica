package com.notionreplica.notesApp.services;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.command.CommandFactory;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.notionreplica.notesApp.services.command.CommandInterface.*;

@Service
@PropertySource("classpath:application.yml")
public class AuthorizationService {
    @Autowired
    private CommandFactory commandFactory;

    public  boolean isPageOwner(Workspace userWorkspace, String pageId) throws Exception{
        return (boolean) commandFactory.create(IS_PAGE_OWNER,userWorkspace,pageId).execute();
    }

    public Workspace isWorkSpaceOwner(String username,String workspaceId) throws Exception{
        return (Workspace) commandFactory.create(IS_WORKSPACE_OWNER,username,workspaceId).execute();
    }

    public boolean isRequesterAuthorized(Workspace userWorkspace, String requesterId) throws Exception{
        return (boolean) commandFactory.create(IS_REQUESTER_AUTHORIZED,userWorkspace,requesterId).execute();
    }
}
