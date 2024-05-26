package com.notionreplica.searchapp.controllers;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.dto.SearchRequest;
import com.notionreplica.searchapp.services.FilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/search/{userName}/workspace/{workspaceId}")
public class FilterController {
    @Autowired
    private FilterService filterService;
    Logger log = LoggerFactory.getLogger(FilterController.class);

    @GetMapping("/notes/{pageId}/filterPages")
    public List<Page> filterIn(@RequestBody SearchRequest searchRequest, @PathVariable("pageId") String pageId, @PathVariable("userName") String userName) throws Exception{
        log.info(userName + "filtered through" + pageId + "with query" + searchRequest.getQuery());
        return filterService.filterIn(searchRequest, pageId, userName);
    }
}