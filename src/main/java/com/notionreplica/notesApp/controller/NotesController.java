package com.notionreplica.notesApp.controller;

import com.notionreplica.notesApp.entities.*;
import com.notionreplica.notesApp.services.FireBaseStorageService;
import com.notionreplica.notesApp.services.NotesService;
import com.notionreplica.notesApp.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user/{userName}/workspace/{workspaceId}/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private FireBaseStorageService fireBaseStorageService;

    @PostMapping("/createPage")
    public ResponseEntity<Map<String, Object>> addPage(@PathVariable("userName") String userName,
                                                       @PathVariable("workspaceId")String workspaceId,
                                                       @RequestParam(name = "accessModifier") String accessModifier,
                                                       @RequestParam(name = "parent") String parent) throws Exception{
//      CompletableFuture<Boolean> userExistsFuture = authorizationService.doesUserExistRequest(userName);
//      boolean userExists = userExistsFuture.get();
//      if(!userExists) throw new UserDoesNotExistException("");
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        Page newPage;
        try {
            newPage = notesService.createPage(userWorkspace.getWorkSpaceId(), AccessModifier.valueOf(accessModifier.toUpperCase()), parent);
        } catch (IllegalArgumentException e) {
            newPage = notesService.createPage(userWorkspace.getWorkSpaceId(), AccessModifier.PUBLIC, parent);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("newPage", newPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getPages(@PathVariable("userName") String userName,
                                                        @PathVariable("workspaceId")String workspaceId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        Map<String, Object> response = new HashMap<>();
        response.put("Pages", notesService.getPages(userWorkspace.getWorkSpaceId()));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getSharedPages/{requesterId}")
    public ResponseEntity<Map<String, Object>> getSharedPages(@PathVariable("userName") String userName,
                                                              @PathVariable("workspaceId")String workspaceId,
                                                              @PathVariable("requesterId")String requesterId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        boolean isRequesterAuthorized = authorizationService.isRequesterAuthorized(userWorkspace,requesterId);
        Map<String, Object> response = new HashMap<>();
        response.put("Pages", notesService.getSharedPages(userWorkspace));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{pageId}")
    public ResponseEntity<Map<String, Object>> getPage(@PathVariable("userName") String userName,
                                                       @PathVariable("pageId") String pageId,
                                                       @PathVariable("workspaceId")String workspaceId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Map<String, Object> response = new HashMap<>();
        response.put("Page", notesService.getPage(pageId, userWorkspace.getWorkSpaceId()));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{pageId}/getMedia")
    public ResponseEntity<Map<String, Object>> getMedia(@PathVariable("userName") String userName,
                                                       @PathVariable("pageId") String pageId,
                                                       @PathVariable("workspaceId")String workspaceId,
                                                        @RequestParam("mediaType") String mediaType) throws Exception {

        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Page currentPage = notesService.getPage(pageId,workspaceId);
        String fileName ="";
        switch (mediaType.toLowerCase()){
            case "background":
                String[] backgroundURLElements = currentPage.getBackgroundURL().split("/");
                fileName= backgroundURLElements[backgroundURLElements.length-1];
                break;
            case "icon":
                String[] iconURLElements = currentPage.getIconURL().split("/");
                fileName= iconURLElements[iconURLElements.length-1];
                break;
            default:
                for (ContentBlock contentBlock: currentPage.getPageContent()){
                    if(contentBlock.getType().equals(ContentType.IMAGE) ){
                        String fileURL = (String) contentBlock.getContent();
                        String fileNameExists = fileURL.split("/")[fileURL.split("/").length-1];
                        if (fileNameExists.equals(mediaType)) {
                            fileName = fileNameExists;
                            break;
                        }
                    }
                }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("Page", fireBaseStorageService.getFile(fileName));

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{pageId}/deleteMedia")
    public ResponseEntity<Map<String, Object>> deleteMedia(@PathVariable("userName") String userName,
                                                           @PathVariable("pageId") String pageId,
                                                           @PathVariable("workspaceId")String workspaceId,
                                                           @RequestParam("mediaType") String mediaType) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Page currentPage = notesService.getPage(pageId,workspaceId);
        String fileName ="";
        switch (mediaType.toLowerCase()){
            case "background":
                String[] backgroundURLElements = currentPage.getBackgroundURL().split("/");
                fileName= backgroundURLElements[backgroundURLElements.length-1];
                break;
            case "icon":
                String[] iconURLElements = currentPage.getIconURL().split("/");
                fileName= iconURLElements[iconURLElements.length-1];
                break;
            default:
                for (ContentBlock contentBlock: currentPage.getPageContent()){
                    if(contentBlock.getType().equals(ContentType.IMAGE) ){
                        String fileURL = (String) contentBlock.getContent();
                        String fileNameExists = fileURL.split("/")[fileURL.split("/").length-1];
                        if (fileNameExists.equals(mediaType)) {
                            fileName = fileNameExists;
                            break;
                        }
                    }
                }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("confirmation", fireBaseStorageService.deleteFile(fileName));
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deletePages")
    public ResponseEntity<Map<String, Object>> deletePages(@PathVariable("userName") String userName,
                                                           @PathVariable("workspaceId")String workspaceId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        Map<String, Object> response = new HashMap<>();
        response.put("Page", notesService.deletePagesByWorkSpaceId(userWorkspace.getWorkSpaceId()));
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{pageId}/deletePage")
    public ResponseEntity<Map<String, Object>> deletePages(@PathVariable("userName") String userName,
                                                           @PathVariable("workspaceId")String workspaceId,
                                                           @PathVariable("pageId")String pageId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Map<String, Object> response = new HashMap<>();
        response.put("Page", notesService.deletePage(pageId,workspaceId));
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{pageId}/updateBackground")
    public ResponseEntity<Map<String, Object>> updateBackground(@PathVariable("userName") String userName,
                                                                @PathVariable("workspaceId")String workspaceId,
                                                                @PathVariable("pageId")String pageId,
                                                                @RequestParam(value = "file", required = false) MultipartFile newBackground)
                                                                throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        String fileUrl = "";
        if (newBackground != null && !newBackground.isEmpty()) {
            fileUrl = fireBaseStorageService.uploadFile(newBackground);
        }
        Page updatedPage = notesService.updatePageBackground(pageId,fileUrl);
        Map<String, Object> response = new HashMap<>();
        response.put("Page",updatedPage);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{pageId}/updateIcon")
    public ResponseEntity<Map<String, Object>> updateIcon(@PathVariable("userName") String userName,
                                                          @PathVariable("workspaceId")String workspaceId,
                                                          @PathVariable("pageId")String pageId,
                                                          @RequestParam(value = "file", required = false) MultipartFile newIcon) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        String fileUrl = "";
        if (newIcon != null && !newIcon.isEmpty()) {
            fileUrl = fireBaseStorageService.uploadFile(newIcon);
        }
        Page updatedPage = notesService.updatePageIcon(pageId,fileUrl);
        Map<String, Object> response = new HashMap<>();
        response.put("Page",updatedPage);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{pageId}/updateTitle")
    public ResponseEntity<Map<String, Object>> updateTitle(@PathVariable("userName") String userName,
                                                          @PathVariable("workspaceId")String workspaceId,
                                                          @PathVariable("pageId")String pageId,
                                                          @RequestBody Map<String,Object> requestBody) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");

        Map<String, Object> response = new HashMap<>();

        if(!(requestBody.get("pageTitle")==null)){
        response.put("Page",notesService.updatePageTitle(pageId,(String)requestBody.get("pageTitle")));
        }else{
            response.put("Page",notesService.updatePageTitle(pageId,"Untitled"));
        }
        return ResponseEntity.ok(response);
    }
//    @PutMapping("/{pageId}/updateContnet")
//    public ResponseEntity<Map<String, Object>> updateContent(@PathVariable("userName") String userName,
//                                                            @PathVariable("workspaceId")String workspaceId,
//                                                            @PathVariable("pageId")String pageId,
//                                                            @RequestBody Map<String,Object> requestBody) throws Exception {
//        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
//        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
//        Page updatedPage =null;
//
//        if(!(requestBody.get("pageContent")==null))
//            updatedPage = notesService.updatePageContent(pageId,(List<ContentBlock>)requestBody.get("pageContent"));
//        Map<String, Object> response = new HashMap<>();
//        response.put("Page",updatedPage);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/{pageId}/movePage")
    public ResponseEntity<Map<String, Object>> movePage(@PathVariable("userName") String userName,
                                                           @PathVariable("workspaceId")String workspaceId,
                                                           @PathVariable("pageId")String pageId,
                                                           @RequestBody Map<String,Object> requestBody) throws Exception {

        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");

        Map<String, Object> response = new HashMap<>();

        if(!(requestBody.get("parentPageId")==null  && requestBody.get("newParentPageId")==null)){
            if(((String)requestBody.get("parentPageId")).equals("")||!authorizationService.isPageOwner(userWorkspace,(String)requestBody.get("parentPageId"))) throw new AccessDeniedException("");
            if(((String)requestBody.get("newParentPageId")).equals("")||!authorizationService.isPageOwner(userWorkspace,(String)requestBody.get("newParentPageId"))) throw new AccessDeniedException("");
            response.put("Page",notesService.moveSubPage(pageId,(String)requestBody.get("parentPageId"),(String)requestBody.get("newParentPageId")));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changeAccessModifier")
    public ResponseEntity<Map<String, Object>> changeAccessModifier(@PathVariable("userName") String userName,
                                                                    @PathVariable("workspaceId")String workspaceId,
                                                                    @RequestParam(name = "accessModifier") String accessModifier,
                                                                    @RequestParam(name = "pageId") String pageId) throws Exception{

        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Workspace newWorkSpace;
        try {
            newWorkSpace = notesService.changeAccessModifier(userWorkspace, AccessModifier.valueOf(accessModifier.toUpperCase()), pageId);
        } catch (IllegalArgumentException e) {
            newWorkSpace = notesService.changeAccessModifier(userWorkspace, AccessModifier.PUBLIC, pageId);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("newPage", newWorkSpace);
        return ResponseEntity.ok(response);
    }
}
