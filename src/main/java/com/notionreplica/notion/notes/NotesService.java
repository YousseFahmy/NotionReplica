package com.notionreplica.notion.notes;

import com.notionreplica.notion.notes.entities.Page;
import com.notionreplica.notion.notes.repositories.PageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    @Autowired
    private PageRepo pageRepo;

    public String addPage(){
        pageRepo.save(new Page(492090 , 492090));
        //return page.save(new Page(123456789,2123456789));
        return "saved";
    }

    public List<Page> tezi2r3a(){
        return pageRepo.findAll();
    }
}
