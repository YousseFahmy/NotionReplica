package com.notionreplica.notesApp.controller;

import com.notionreplica.notesApp.Services.NotesService;
import com.notionreplica.notesApp.entities.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Notes")
public class NotesController {
    @Autowired
    private NotesService notesService;
    @PostMapping("/addPage/{workSpaceId}")
    public String addPage(){
        notesService.addPage();
        return "saved";
    }
    @GetMapping("/getPages/{workSpaceId}")
    public List<Page> getPages(){
        return notesService.getPages();
    }
}
