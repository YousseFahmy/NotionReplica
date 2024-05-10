package com.notes;

import com.notes.entities.Page;
import com.notes.repositories.PageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotesController {
    @Autowired
    PageRepo page;
    @PostMapping("/addPage")
    public Page addPage(){
        page.save(new Page(33,34));
        return page.save(new Page(33,34));

    }
}
