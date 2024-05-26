package com.notionreplica.searchapp.services.command;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.dto.Workspace;
import com.notionreplica.searchapp.repositories.PageRepo;
import com.notionreplica.searchapp.repositories.WorkspaceRepo;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class SearchPages implements CommandInterface {
    WorkspaceRepo workspaceRepo;
    PageRepo pageRepo;

    String query;
    String userName;

    @Override
    public Object execute() throws Exception {
        List<Page> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        Workspace userWorkspace = workspaceRepo.findWorkspaceByUserName(userName);

        if(userWorkspace == null) throw new Exception("Wala enta 3abeet");

        Set<String> userPages = userWorkspace.getAccessModifiers().keySet();
        List<Page> pages = pageRepo.findAllById(userPages);

        for (Page page : pages) {
            Matcher titleMatcher = pattern.matcher(page.getPageTitle());
            if(titleMatcher.find()) {
                result.add(page);
            }
        }
        return result;
    }
}
