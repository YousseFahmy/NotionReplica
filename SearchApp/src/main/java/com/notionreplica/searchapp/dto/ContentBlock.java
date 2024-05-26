package com.notionreplica.searchapp.dto;

import lombok.Data;

import java.util.List;
@Data
public class ContentBlock {
    private ContentType type;
    private Object content;
    private List<ContentBlock> children;
}
