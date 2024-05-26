package com.notionreplica.searchapp.controllers;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.dto.SearchRequest;
import com.notionreplica.searchapp.services.SortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search/{userName}/workspace/{workspaceId}/sort")
public class SortController {
    @Autowired
    private SortService sortService;
    Logger log = LoggerFactory.getLogger(SortController.class);

    @GetMapping("")
    public List<Page> sortPages(@RequestBody SearchRequest searchRequest, @PathVariable("userName") String userName) throws Exception {
        log.info(userName + "sorted by" + searchRequest.getFunctionality() + "with query" + searchRequest.getQuery());
        return sortService.sortPages(searchRequest, userName);
    }
}
