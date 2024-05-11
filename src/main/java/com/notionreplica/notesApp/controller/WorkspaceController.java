package com.notionreplica.notesApp.controller;

import com.notionreplica.notesApp.Services.WorkSpaceService;
import com.notionreplica.notesApp.entities.Page;
import com.notionreplica.notesApp.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("WorkSpace")
public class WorkspaceController {
    @Autowired
    private WorkSpaceService workSpaceService;
    @PostMapping("/createWorkSpace/{userId}")
    public Workspace createWorkSpace(@PathVariable("userId") long userId){
        return workSpaceService.createWorkSpace(userId);
    }
}
