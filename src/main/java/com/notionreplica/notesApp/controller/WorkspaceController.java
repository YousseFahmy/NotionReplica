package com.notionreplica.notesApp.controller;

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
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getWorkSpace(@PathVariable("userId") long userId){
        Workspace userWorkSpace = workSpaceService.getWorkSpace(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("workspace",userWorkSpace);
        //response.put("pages",pagesNames);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/createWorkSpace")
    public ResponseEntity<Map<String, Object>> createWorkSpace(@PathVariable("userId") long userId){
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace =workSpaceService.createWorkSpace(userId);
        response.put("workSpace",userWorkSpace);
        return ResponseEntity.ok(response);
    }


}
