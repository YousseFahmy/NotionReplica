package com.notionreplica.udbs.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "UDBPages")
public class UDBPage {

    @Setter(AccessLevel.PROTECTED)
    @MongoId(FieldType.OBJECT_ID)
    private String udbPageID;

    @Field
    private String pageID; //Coming from 2nd team

    @Field
    private String dataTableID;

//    @Field
//    private LinkedHashMap<Properties,Object> properties;



    @CreatedDate
    @Field("created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Date updatedAt;
}
