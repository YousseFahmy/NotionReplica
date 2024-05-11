package com.notionreplica.notesApp.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Pages")
public class Page {

    @Setter(AccessLevel.PROTECTED)
    @MongoId(FieldType.OBJECT_ID)
    private String pageId;

    @DBRef
    @Field
    private Workspace workspace;

    @Field
    private long iconId = -1;

    @Field
    private long backgroundId = -1;

    @DBRef
    private Set<Page> subPagesIds  = new HashSet<Page>();

    @Field
    private Set<Long> UDBIds = new HashSet<Long>();

    @Field
    private Map<String,String>  pageContent = new HashMap<>();

    @Field
    private String pageTitle = "Untitled";

    @CreatedDate
    @Field("created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Date updatedAt;

    public Page(Workspace workspace,String pageTitle) {
        this.workspace= workspace;
        this.pageTitle= pageTitle==null?"untitled":pageTitle;
    }
}