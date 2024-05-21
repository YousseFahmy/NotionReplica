package com.notionreplica.udbs.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic pageRequestTopic(){
        return TopicBuilder.name("pageRequestTopic").build();
    }

//    @Bean
//    public Map<String, CompletableFuture<String>> pendingRequests() {
//        return new ConcurrentHashMap<>();
//    }
}

