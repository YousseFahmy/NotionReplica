package com.notionreplica.udbs.entities;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "UDBs")
public class UDBDataTable {

    @Setter(AccessLevel.PROTECTED)
    @MongoId(FieldType.OBJECT_ID)
    private String udbDataTableID;

    @Field
    private String udbDataTableTitle;

    @Field
    private LinkedHashSet<Properties> properties = new LinkedHashSet<>();

    @Field
    private LinkedHashSet<String> udbPages = new LinkedHashSet<>();


    @CreatedDate
    @Field("created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Date updatedAt;
}
