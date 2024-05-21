package com.notionreplica.udbs.controller;

import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.services.UDBPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/user/{userName}/workspace/{workspaceId}/notes/{pageID}/udbTable/{UDBid}")
public class UDBPageController {
    @Autowired
    private UDBPageService pageService;


    @PostMapping("/createPage")
    public ResponseEntity<Map<String, Object>> createPage( @PathVariable("userName") String username,
                                                           @PathVariable("workspaceId") String workspaceId,
                                                           @PathVariable("pageID") String pageID,
                                                           @PathVariable("UDBid") String tableID) throws Exception {
        CompletableFuture<String> pageCreationRequest = pageService.createPageRequest(username,workspaceId,pageID);
        String udbPageID = pageCreationRequest.get();
        if(udbPageID==null) throw new AccessDeniedException("Ask btoo3 notes");

        UDBPage page = pageService.createUDBPage(udbPageID, tableID);
        Map<String, Object> response = new HashMap<>();
        response.put("UDB page", page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page/{udbPageID}")
    public ResponseEntity<Map<String, Object>> getPage(@PathVariable("udbPageID") String udbPageID) throws Exception {
        UDBPage page = pageService.getUDBPage(udbPageID);
        Map<String, Object> response = new HashMap<>();
        response.put("UDB page", page);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/page/{udbPageID}")
    public ResponseEntity<String> deletePage(@PathVariable("udbPageID") String udbPageID) throws Exception {
        pageService.deleteUDBPage(udbPageID);
        return ResponseEntity.ok("Deleted UDB page");
    }
}
