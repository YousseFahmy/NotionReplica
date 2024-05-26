package com.notionreplica.searchapp.services;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.services.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

import static com.notionreplica.searchapp.services.command.CommandInterface.*;

@Service
public class PageSearchService {

    @Autowired
    private CommandFactory commandFactory;

    @ResponseStatus(HttpStatus.OK)
    public List<Page> searchPages(String query, String userName) throws Exception {
        return (List<Page>) commandFactory.create(SEARCH_PAGES, query, userName).execute();
    }

}
