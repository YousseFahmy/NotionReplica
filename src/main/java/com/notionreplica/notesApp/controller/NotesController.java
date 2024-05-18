package com.notionreplica.notesApp.controller;

import com.notionreplica.notesApp.exceptions.UserDoesNotExistException;
import com.notionreplica.notesApp.entities.AccessModifier;
import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.entities.Workspace;
import com.notionreplica.notesApp.services.NotesService;
import com.notionreplica.notesApp.services.AuthorizationService;
import com.notionreplica.notesApp.services.command.CommandInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/user/{userId}/workspace/{workspaceId}/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/createPage")
    public ResponseEntity<Map<String, Object>> addPage(@PathVariable("userId") UUID userId,
                                                       @PathVariable("workspaceId")String workspaceId,
                                                       @RequestParam(name = "accessModifier") String accessModifier,
                                                       @RequestParam(name = "parent") String parent) throws Exception{
        CompletableFuture<Boolean> userExistsFuture = authorizationService.doesUserExistRequest(userId);
        boolean userExists = userExistsFuture.get();
        if(!userExists) throw new UserDoesNotExistException("");
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userId,workspaceId);
        Page newPage;
        try {
            newPage = notesService.createPage(userWorkspace.getWorkSpaceId(), AccessModifier.valueOf(accessModifier.toUpperCase()), parent);
        } catch (IllegalArgumentException e) {
            newPage = notesService.createPage(userWorkspace.getWorkSpaceId(), AccessModifier.PUBLIC, parent);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("newPage", newPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getPages(@PathVariable("userId") UUID userId,
                                                        @PathVariable("workspaceId")String workspaceId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userId,workspaceId);
        Map<String, Object> response = new HashMap<>();
        response.put("Pages", notesService.getPages(userWorkspace.getWorkSpaceId()));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getSharedPages/{requesterId}")
    public ResponseEntity<Map<String, Object>> getSharedPages(@PathVariable("userId") UUID userId,
                                                              @PathVariable("workspaceId")String workspaceId,
                                                              @PathVariable("requesterId")UUID requesterId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userId,workspaceId);
        boolean isRequesterAuthorized = authorizationService.isRequesterAuthorized(userWorkspace,requesterId);
        Map<String, Object> response = new HashMap<>();
        response.put("Pages", notesService.getSharedPages(userWorkspace));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{pageId}")
    public ResponseEntity<Map<String, Object>> getPage(@PathVariable("userId") UUID userId,
                                                       @PathVariable("pageId") String pageId,
                                                       @PathVariable("workspaceId")String workspaceId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userId,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Map<String, Object> response = new HashMap<>();
        response.put("Page", notesService.getPage(pageId, userWorkspace.getWorkSpaceId()));
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deletePages")
    public ResponseEntity<Map<String, Object>> deletePages(@PathVariable("userId") UUID userId,
                                                           @PathVariable("workspaceId")String workspaceId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userId,workspaceId);
        Map<String, Object> response = new HashMap<>();
        response.put("Page", notesService.deletePagesByWorkSpaceId(userWorkspace.getWorkSpaceId()));
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{pageId}/deletePage")
    public ResponseEntity<Map<String, Object>> deletePages(@PathVariable("userId") UUID userId,
                                                           @PathVariable("workspaceId")String workspaceId,
                                                           @PathVariable("pageId")String pageId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userId,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Map<String, Object> response = new HashMap<>();
        response.put("Page", notesService.deletePage(pageId,workspaceId));
        return ResponseEntity.ok(response);
    }


}
