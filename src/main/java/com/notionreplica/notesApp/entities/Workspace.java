package com.notionreplica.notesApp.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.IndexOptions;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.data.redis.core.index.Indexed;

import java.util.*;

@Data
@Document("Workspace")
public class Workspace {

    @Setter(AccessLevel.PROTECTED)
    @MongoId(FieldType.OBJECT_ID)
    private String workspaceId;

    @Field
    private long userId ;

    @Field
    private Set<Long> usersWithAccess = new HashSet<Long>();

    @DBRef
    private Map <Page, AccessModifier> accessModifiers = new HashMap<Page,AccessModifier>();

    @CreatedDate
    @Field("created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Date updatedAt;
    public Workspace(long userId){
        this.userId= userId;
    }
}
