package com.notionreplica.notion.notes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Document("Workspace")
public class Workspace {

    public Workspace(){
        userId=30;

    }
    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID workspaceId;
    private long userId ;


    private List<Long> usersWithAccess = new ArrayList<>();
    private Map <Long, AccessModifier> accessModifiers = new HashMap<>();
    private LocalDateTime creationDate;

}
