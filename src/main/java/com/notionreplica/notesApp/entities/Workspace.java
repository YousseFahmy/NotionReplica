package com.notionreplica.notesApp.entities;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Data
@Document("Workspace")
public class Workspace {

    @Setter(AccessLevel.PROTECTED)
    @MongoId(FieldType.OBJECT_ID)
    private String workSpaceId;

    @Field
    @Indexed(unique = true)
    private String userName ;

    @Field
    private Set<String> usersWithAccess = new HashSet<String>();
    @Field
    private Map <String, AccessModifier> accessModifiers = new HashMap<String,AccessModifier>();

    @CreatedDate
    @Field("created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Date updatedAt;
    public Workspace(String userName){
        this.userName= userName;
    }
}
