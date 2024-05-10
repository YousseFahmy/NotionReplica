package com.notionreplica.notion.notes;

import com.notionreplica.notion.notes.entities.Page;
import com.notionreplica.notion.notes.entities.Workspace;
import com.notionreplica.notion.notes.repositories.PageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotesController {
    @Autowired
    private NotesService notesService;
    @PostMapping("/addPage")
    public String addPage(){
        notesService.addPage();
        return "saved";
    }
    @GetMapping("/addPage")
    public List<Page> tezi2r3a(){
        return notesService.tezi2r3a();
    }
}
