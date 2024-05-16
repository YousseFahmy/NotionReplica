package com.notionreplica.udbs.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Properties")
public class Properties {

    @Setter(AccessLevel.PROTECTED)
    @MongoId(FieldType.OBJECT_ID)
    private String propertyID;

    @Field
    private PropertyType type;

    @Field
    private String title;

    //<Config Type, Config Value>
    @Field
    private LinkedHashMap<String, String> configurations;
}
