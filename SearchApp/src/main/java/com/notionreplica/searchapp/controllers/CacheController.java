package com.notionreplica.searchapp.controllers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

@PropertySource("classpath:application.yml")
@RequiredArgsConstructor
@RestController
@RequestMapping("/search/{userName}/workspace/{workspaceId}/cachedQueries")
public class CacheController {

    @Value("${spring.redis.url}")
    private String redisURL;
    private Jedis jedis;
    @PostConstruct
    public void init() {
        jedis = new Jedis(redisURL);
    }
    Logger log = LoggerFactory.getLogger(CacheController.class);

    @GetMapping("")
    public List<String> getCachedQueries(@PathVariable("userName") String userName) {
        log.info(userName + "got his most recent queries");
        return Arrays.asList(jedis.get(userName).split(","));
    }
}
