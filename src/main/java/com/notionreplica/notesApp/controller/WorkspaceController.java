package com.notionreplica.notesApp.controller;

import com.notionreplica.notesApp.services.AuthorizationService;
import com.notionreplica.notesApp.services.WorkSpaceService;
import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/{userId}/workspace")
public class WorkspaceController extends Throwable{
    @Autowired
    private WorkSpaceService workSpaceService;
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/createWorkSpace")
    public ResponseEntity<Map<String, Object>> createWorkSpace(@PathVariable("userId") long userId) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace =workSpaceService.createWorkSpace(userId);
        response.put("workSpace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getWorkSpace(@PathVariable("userId") long userId) throws Exception{
        Workspace userWorkSpace = workSpaceService.getWorkSpace(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/addUserToWorkSpace")
    public ResponseEntity<Map<String, Object>> addUserToWorkSpace(@PathVariable("userId") long userId,
                                                                  @RequestBody Map<String,String> request)throws Exception{
        long newUserId=Long.parseLong(request.get("newUserId")) ;
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace = workSpaceService.addUserToWorkspace(userId, newUserId);
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteWorkSpace")
    public ResponseEntity<Map<String, Object>> deleteWorkSpace(@PathVariable("userId") long userId) throws Exception{
        Map<String, Object> response = new HashMap<>();
        String userWorkSpace =workSpaceService.deleteWorkSpace(userId);
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
}