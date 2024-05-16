package com.notionreplica.notesApp.controller;

import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.exceptions.InvalidObjectIdException;
import com.notionreplica.notesApp.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/{userId}/workspace/{workspaceId}/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @PostMapping("/createPage")
    public ResponseEntity<Map<String, Object>> addPage(@PathVariable("userId") String userId,
                                                       @PathVariable("workspaceId") String workspaceId,
                                                       @RequestParam(name = "accessModifier") String accessModifier,
                                                       @RequestParam(name = "parent") String parent) {
        Page newPage =null;
        try {
            newPage = notesService.createPage(workspaceId, AccessModifier.valueOf(accessModifier.toUpperCase()), parent);
        } catch (IllegalArgumentException e) {
            newPage = notesService.createPage(workspaceId, AccessModifier.PUBLIC, parent);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("newPage",newPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getPages(@PathVariable("userId") String userId,
                                                        @PathVariable("workspaceId") String workspaceId) {
        Map<String, Object> response = new HashMap<>();
        response.put("Pages",notesService.getPages(workspaceId));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{pageId}")
    public ResponseEntity<Map<String, Object>> getPage(@PathVariable("userId") String userId,
                                                        @PathVariable("workspaceId") String workspaceId,
                                                       @PathVariable("pageId")String pageId){
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("Page",notesService.getPage(pageId,workspaceId));
        }
        catch(Exception e){
            response.put("error message",e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletePages")
    public ResponseEntity<Map<String, Object>> deletePages(@PathVariable("userId") String userId,
                                                       @PathVariable("workspaceId") String workspaceId){
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("Page",notesService.deletePagesByWorkSpaceId(workspaceId));
        }
        catch(Exception e){
            response.put("error message",e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
