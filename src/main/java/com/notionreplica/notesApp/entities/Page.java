package com.notionreplica.notesApp.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Data
@Document(collection = "Pages")
public class Page {

    @Setter(AccessLevel.PROTECTED)
    @MongoId(FieldType.OBJECT_ID)
    private String pageId;

    @Field
    private String workspaceId;

    @Field
    private String iconURL="";

    @Field
    private String backgroundURL="";

    @Field
    private Set<String> subPagesIds  = new HashSet<String>();

    @Field
    private Set<String> UDBIds = new HashSet<>();

    @Field
    private List<ContentBlock> pageContent = new ArrayList<>();

    @Field
    private String pageTitle = "Untitled";

    @CreatedDate
    @Field("created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Date updatedAt;

    public Page(String workspaceId) {
        this.workspaceId= workspaceId;
    }


}