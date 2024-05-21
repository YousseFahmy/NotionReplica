package com.notionreplica.udbs.controller;

import com.notionreplica.udbs.entities.UDBPage;
import com.notionreplica.udbs.services.UDBPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/{userId}/workspace/{workspaceId}/notes/udbTable/{UDBid}")
public class UDBPageController {
    @Autowired
    private UDBPageService pageService;


    @PostMapping("/createPage")
    public ResponseEntity<Map<String, Object>> createPage(@RequestBody Map<String,String> reqBody, @PathVariable("UDBid") String tableID) throws Exception {
        UDBPage page = pageService.createUDBPage( reqBody.get("pageID"), tableID);
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
