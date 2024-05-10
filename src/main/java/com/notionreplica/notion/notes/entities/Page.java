package com.notionreplica.notion.notes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Page")
public class Page {

    public Page(long workspaceId, long iconId){
        this.iconId = iconId;
        this.workspaceId = workspaceId;
    }

    private long workspaceId;

    @Id
    //@Setter(AccessLevel.PROTECTED)
    //@GeneratedValue(strategy = GenerationType.UUID)
    private Integer pageId;

    private long iconId = -1;
    //private long backgroundId = -1;

    //private List<Long> subPagesIds = new ArrayList<>();
    //private List<Long> UDBIds = new ArrayList<>();
    //private LocalDateTime creationDate;
   // private String pageContent = "";
    //private String pageTitle = "Untitled";




}