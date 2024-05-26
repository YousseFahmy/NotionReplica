package com.notionreplica.searchapp.controllers;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.dto.SearchRequest;
import com.notionreplica.searchapp.dto.SearchResponse;
import com.notionreplica.searchapp.exceptions.NoSearchResultsException;
import com.notionreplica.searchapp.services.PageSearchService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.*;

@PropertySource("classpath:application.yml")
@RequiredArgsConstructor
@RestController
@RequestMapping("/search/{userName}/workspace/{workspaceId}/search")
public class SearchController {

    private final int queryLimit = 5;
    private final PageSearchService pageSearchService;
    @Value("${spring.redis.url}")
    private String redisURL;
    private Jedis jedis;
    @PostConstruct
    public void init() {
        jedis = new Jedis(redisURL);
    }

    Logger log = LoggerFactory.getLogger(SearchController.class);

    @GetMapping("")
    public SearchResponse searchPages(@RequestBody SearchRequest searchRequest, @PathVariable("userName") String userName) throws Exception {
        List<Page> pages = pageSearchService.searchPages(searchRequest.getQuery(), userName);
        SearchResponse searchResponse = new SearchResponse(pages);

        if(pages.isEmpty()) throw new NoSearchResultsException("No Search Results");

        if(!jedis.exists(userName)) {
            jedis.set(userName, searchRequest.getQuery());
        } else {
            String queries = jedis.get(userName);
            LinkedList<String> queriesSplit = new LinkedList<>(Arrays.asList(queries.split(",")));
            if(!queriesSplit.contains(searchRequest.getQuery())) {
                if(queriesSplit.size() == queryLimit) {
                    queriesSplit.remove(0);
                    queries = "";
                    for(int i = 0; i < queriesSplit.size(); i++) {
                        queries += queriesSplit.get(i) + ",";
                    }
                    queries += searchRequest.getQuery();
                    jedis.set(userName, queries);
                } else {
                    queries += "," + searchRequest.getQuery();
                    jedis.set(userName, queries);
                }
            }

        }
        log.info(userName + "searched with query" + searchRequest.getQuery());
        return searchResponse;
    }
}
