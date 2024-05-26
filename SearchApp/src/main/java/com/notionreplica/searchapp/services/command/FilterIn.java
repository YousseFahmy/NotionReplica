package com.notionreplica.searchapp.services.command;

import com.notionreplica.searchapp.dto.Page;
import com.notionreplica.searchapp.dto.Workspace;
import com.notionreplica.searchapp.repositories.PageRepo;
import com.notionreplica.searchapp.repositories.WorkspaceRepo;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class FilterIn implements CommandInterface {
    WorkspaceRepo workspaceRepo;
    PageRepo pageRepo;

    String query;
    String pageId;
    String userName;

    @Override
    public Object execute() throws Exception {
        List<Page> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);

        Workspace userWorkspace = workspaceRepo.findWorkspaceByUserName(userName);

        if(userWorkspace == null) throw new Exception("Wala enta 3abeet");

        if(!userWorkspace.getAccessModifiers().keySet().contains(pageId)) {
            throw new Exception("Wala enta 3abeet t2reeban");
        }

        Optional<Page> parentPageExists = pageRepo.findById(pageId);
        if(!parentPageExists.isPresent()) {
            throw new Exception("Wala enta 3abeet awi");
        }

        Page parentPage = parentPageExists.get();

        ArrayList<String> children = new ArrayList<>(parentPage.getSubPagesIds());

        List<Page> pages = pageRepo.findAllById(children);
        pages.add(parentPage);

        for (Page page : pages) {
            Matcher titleMatcher = pattern.matcher(page.getPageTitle());
            if(titleMatcher.find()) {
                result.add(page);
            }
        }
        return result;
    }
}
