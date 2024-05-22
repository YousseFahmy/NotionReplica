package com.notionreplica.notesApp.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.Date;
import java.util.List;
@Data
public class ContentBlock {
    private ContentType type;
    private Object content;
    private List<ContentBlock> children;
}
