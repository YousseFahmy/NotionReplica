package com.notionreplica.searchapp.services.command;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.dto.Workspace;
import com.notionreplica.searchapp.repositories.PageRepo;
import com.notionreplica.searchapp.repositories.WorkspaceRepo;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Sort implements CommandInterface{
    WorkspaceRepo workspaceRepo;
    PageRepo pageRepo;

    String query;
    String functionality;
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

        // Check for sorting functionality using regular expressions
        if (functionality.equals("LatestCreated")) {
            result = result.stream()
                    .sorted(Comparator.comparing(Page::getCreatedAt).reversed())
                    .collect(Collectors.toList());
        } else if (functionality.equals("OldestCreated")) {
            result = result.stream()
                    .sorted(Comparator.comparing(Page::getCreatedAt))
                    .collect(Collectors.toList());
        } else if (functionality.equals("LatestModified")) {
            result = result.stream()
                    .sorted(Comparator.comparing(Page::getUpdatedAt).reversed())
                    .collect(Collectors.toList());
        } else if (functionality.equals("OldestModified")) {
            result = result.stream()
                    .sorted(Comparator.comparing(Page::getUpdatedAt))
                    .collect(Collectors.toList());
        }

        return result;

    }
}
