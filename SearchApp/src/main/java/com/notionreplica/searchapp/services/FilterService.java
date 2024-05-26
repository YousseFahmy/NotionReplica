package com.notionreplica.searchapp.services;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.dto.SearchRequest;
import com.notionreplica.searchapp.services.command.CommandFactory;
import com.notionreplica.searchapp.services.command.CommandInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilterService {

    @Autowired
    private CommandFactory commandFactory;

    public List<Page> filterIn(SearchRequest searchRequest, String pageId, String userName) throws Exception {
        return (List<Page>) commandFactory.create(CommandInterface.FILTER_IN, searchRequest.getQuery(), pageId, userName).execute();
    }
}
