package com.notionreplica.notesApp.controller;
import com.notionreplica.notesApp.services.AuthorizationService;
import com.notionreplica.notesApp.services.WorkSpaceService;
import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/{userName}/workspace")
public class WorkspaceController extends Throwable{
    @Autowired
    private WorkSpaceService workSpaceService;
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/createWorkSpace")
    public ResponseEntity<Map<String, Object>> createWorkSpace(@PathVariable("userName") String userName) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace =workSpaceService.createWorkSpace(userName);
        response.put("workSpace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getWorkSpace(@PathVariable("userName") String userName) throws Exception{
        Workspace userWorkSpace = workSpaceService.getWorkSpace(userName);
        Map<String, Object> response = new HashMap<>();
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/addUserToWorkSpace")
    public ResponseEntity<Map<String, Object>> addUserToWorkSpace(@PathVariable("userName") String userName,
                                                                  @RequestBody Map<String,String> request)throws Exception{
        if(request.get("newUserName")==null) throw new Exception("Please provide a user to add");
        String newUserName= request.get("newUserName");
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace = workSpaceService.addUserToWorkspace(userName, newUserName);
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/removeUserFromWorkSpace")
    public ResponseEntity<Map<String, Object>> removeUserFromWorkSpace(@PathVariable("userName") String userName,
                                                                  @RequestBody Map<String,String> request)throws Exception{
        if(request.get("userNameToRemove")==null) throw new Exception("Please provide a user to remove");
        String userNameToRemove = request.get("userNameToRemove");
        Map<String, Object> response = new HashMap<>();
        Workspace userWorkSpace = workSpaceService.removeUserFromWorkspace(userName, userNameToRemove );
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteWorkSpace")
    public ResponseEntity<Map<String, Object>> deleteWorkSpace(@PathVariable("userName") String userName) throws Exception{
        Map<String, Object> response = new HashMap<>();
        String userWorkSpace =workSpaceService.deleteWorkSpace(userName);
        response.put("workspace",userWorkSpace);
        return ResponseEntity.ok(response);
    }
}