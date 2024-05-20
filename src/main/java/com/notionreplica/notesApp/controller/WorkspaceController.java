package com.notionreplica.notesApp.controller;

import com.notionreplica.notesApp.services.AuthorizationService;
import com.notionreplica.notesApp.services.WorkSpaceService;
import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.UnsatisfiedRequestParameterException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user/{userId}/workspace")
public class WorkspaceController extends Throwable{
    @Autowired
    private WorkSpaceService workSpaceService;
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/createWorkSpace")
    public ResponseEntity<Map<String, Object>> createWorkSpace(@PathVariable("userId") UUID userId) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace =workSpaceService.createWorkSpace(userId);
        response.put("workSpace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getWorkSpace(@PathVariable("userId") UUID userId) throws Exception{
        Workspace userWorkSpace = workSpaceService.getWorkSpace(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/addUserToWorkSpace")
    public ResponseEntity<Map<String, Object>> addUserToWorkSpace(@PathVariable("userId") UUID userId,
                                                                  @RequestBody Map<String,String> request)throws Exception{
        if(request.get("newUserId")==null) throw new Exception("Please provide a user to add");
        UUID newUserId= UUID.fromString(request.get("newUserId"));
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace = workSpaceService.addUserToWorkspace(userId, newUserId);
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/removeUserFromWorkSpace")
    public ResponseEntity<Map<String, Object>> removeUserFromWorkSpace(@PathVariable("userId") UUID userId,
                                                                  @RequestBody Map<String,String> request)throws Exception{
        if(request.get("userIdToRemove")==null) throw new Exception("Please provide a user to remove");
        UUID userIdToRemove = UUID.fromString(request.get("userIdToRemove"));
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace = workSpaceService.removeUserFromWorkspace(userId, userIdToRemove );
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteWorkSpace")
    public ResponseEntity<Map<String, Object>> deleteWorkSpace(@PathVariable("userId") UUID userId) throws Exception{
        Map<String, Object> response = new HashMap<>();
        String userWorkSpace =workSpaceService.deleteWorkSpace(userId);
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
}