package com.notionreplica.searchapp.services;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.dto.SearchRequest;
import com.notionreplica.searchapp.services.command.CommandFactory;
import com.notionreplica.searchapp.services.command.CommandInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class SortService {

    @Autowired
    private CommandFactory commandFactory;

    @ResponseStatus(HttpStatus.OK)
    public List<Page> sortPages(SearchRequest searchRequest, String userName) throws Exception {
        return (List<Page>) commandFactory.create(CommandInterface.SORT, searchRequest.getQuery(), searchRequest.getFunctionality(), userName).execute();
    }
}
