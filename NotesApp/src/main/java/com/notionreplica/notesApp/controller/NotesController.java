package com.notionreplica.notesApp.controller;

import com.notionreplica.notesApp.entities.*;
import com.notionreplica.notesApp.exceptions.UserDoesNotExistException;
import com.notionreplica.notesApp.services.FireBaseStorageService;
import com.notionreplica.notesApp.services.KafkaService;
import com.notionreplica.notesApp.services.NotesService;
import com.notionreplica.notesApp.services.AuthorizationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user/{userName}/workspace/{workspaceId}/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private FireBaseStorageService fireBaseStorageService;
    @Autowired
    private KafkaService kafkaService;
    Logger log = LoggerFactory.getLogger(NotesController.class);

    @PostMapping("/createPage")
    public ResponseEntity<Map<String, Object>> addPage(@PathVariable("userName") String userName,
                                                       @PathVariable("workspaceId")String workspaceId,
                                                       @RequestParam(name = "accessModifier") String accessModifier,
                                                       @RequestParam(name = "parent") String parent) throws Exception{
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);

        Page newPage;
        try {
            newPage = notesService.createPage(userWorkspace.getWorkSpaceId(), AccessModifier.valueOf(accessModifier.toUpperCase()), parent);
        } catch (IllegalArgumentException e) {
            newPage = notesService.createPage(userWorkspace.getWorkSpaceId(), AccessModifier.PUBLIC, parent);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("newPage", newPage);
        log.info("user:"+ userName + "created in his workspace with id :" +workspaceId +"page with id :" + newPage.getPageId() + "and access modifer" + accessModifier);
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
                                                       @PathVariable("workspaceId")String workspaceId,
                                                       @PathVariable("pageId") String pageId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Map<String, Object> response = new HashMap<>();
        response.put("Page", notesService.getPage(pageId, userWorkspace.getWorkSpaceId()));
        log.info("user:"+ userName + "opened from his workspace with id :" +workspaceId +"page with id :" + pageId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{pageId}/getMedia")
    public ResponseEntity<Map<String, Object>> getMedia(@PathVariable("userName") String userName,
                                                        @PathVariable("workspaceId")String workspaceId,
                                                        @PathVariable("pageId") String pageId,
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
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId +"and in page with id :" + pageId + "accessed the media named" +fileName);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{pageId}/deleteMedia")
    public ResponseEntity<Map<String, Object>> deleteMedia(@PathVariable("userName") String userName,
                                                           @PathVariable("workspaceId")String workspaceId,
                                                           @PathVariable("pageId") String pageId,
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
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId +"and in page with id :" + pageId + "deleted the media named" +fileName);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deletePages")
    public ResponseEntity<Map<String, Object>> deletePages(@PathVariable("userName") String userName,
                                                           @PathVariable("workspaceId")String workspaceId) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        Map<String, Object> response = new HashMap<>();
        response.put("Page", notesService.deletePagesByWorkSpaceId(userWorkspace.getWorkSpaceId()));
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId +"deleted all pages");
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
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId +"deleted page with id :" + pageId);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{pageId}/updateBackground")
    public ResponseEntity<Map<String, Object>> updateBackground(@PathVariable("userName") String userName,
                                                                @PathVariable("workspaceId")String workspaceId,
                                                                @PathVariable("pageId")String pageId,
                                                                @RequestParam(value = "file", required = false) MultipartFile newBackground) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        String fileUrl = "";
        if (newBackground != null && !newBackground.isEmpty()) {
            fileUrl = fireBaseStorageService.uploadFile(newBackground);
        }
        Page updatedPage = notesService.updatePageBackground(pageId,fileUrl);
        Map<String, Object> response = new HashMap<>();
        response.put("Page",updatedPage);
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId +"updated the background to page with id :" + pageId +"to" +fileUrl);
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
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId +"updated the icon to page with id :" + pageId +"to" +fileUrl);

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
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId +"updated the title to page with id :" + pageId +"to" +(String)requestBody.get("pageTitle"));

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{pageId}/updateContnet")
    public ResponseEntity<Map<String, Object>> updateContent(@PathVariable("userName") String userName,
                                                            @PathVariable("workspaceId")String workspaceId,
                                                            @PathVariable("pageId")String pageId,
                                                            @RequestBody Map<String,Object> requestBody) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Page updatedPage =null;
        if(!(requestBody.get("pageContent")==null))
            updatedPage = notesService.updatePageContent(pageId,(List<ContentBlock>)requestBody.get("pageContent"));
        Map<String, Object> response = new HashMap<>();
        response.put("Page",updatedPage);
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId +"updated the content to page with id :" + pageId);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{pageId}/movePage")
    public ResponseEntity<Map<String, Object>> movePage(@PathVariable("userName") String userName,
                                                        @PathVariable("workspaceId")String workspaceId,
                                                        @PathVariable("pageId")String pageId,
                                                        @RequestBody Map<String,Object> requestBody) throws Exception {
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        String parentPageId = (String) requestBody.get("parentPageId");
        String newparentPageId = (String) requestBody.get("newParentPageId");

        if(parentPageId== null || newparentPageId == null) throw new Exception("missing parameters in request body");
        Map<String, Object> response = new HashMap<>();

        if(!(parentPageId.equals("")
           ||authorizationService.isPageOwner(userWorkspace,parentPageId)))
            throw new AccessDeniedException("");
        if(!(newparentPageId.equals("")
                ||authorizationService.isPageOwner(userWorkspace,newparentPageId)))
            throw new AccessDeniedException("");
        response.put("Page",notesService.moveSubPage(pageId,(String)requestBody.get("parentPageId"),(String)requestBody.get("newParentPageId")));
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId + "moved page with id :"+ pageId+ "from parent id:" + parentPageId +"to new parent id"+ newparentPageId);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{pageId}/changeAccessModifier")
    public ResponseEntity<Map<String, Object>> changeAccessModifier(@PathVariable("userName") String userName,
                                                                    @PathVariable("workspaceId")String workspaceId,
                                                                    @PathVariable(name = "pageId") String pageId,
                                                                    @RequestParam(name = "accessModifier") String accessModifier) throws Exception{
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
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId + "changed the access modifier to the page with id :"+ pageId+"to"+ accessModifier);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{pageId}/addUDB")
    public ResponseEntity<Map<String, Object>> addUDB( @PathVariable("userName") String userName,
                                                       @PathVariable("workspaceId")String workspaceId,
                                                       @PathVariable(name = "pageId") String pageId) throws Exception{
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        String udbId = kafkaService.createUDB("Untitled").get(15, TimeUnit.SECONDS);
        Page updatedPage = notesService.addUDB(pageId,udbId);
        Map<String, Object> response = new HashMap<>();
        response.put("newPage",updatedPage);
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId + "added to page with id :"+ pageId+ "a udb with id" + udbId);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{pageId}/deleteUDB/{udbId}")
    public ResponseEntity<Map<String, Object>> deleteUDB( @PathVariable("userName") String userName,
                                                          @PathVariable("workspaceId")String workspaceId,
                                                          @PathVariable(name = "pageId") String pageId,
                                                          @PathVariable(name = "udbId") String udbId) throws Exception{
        Workspace userWorkspace = authorizationService.isWorkSpaceOwner(userName,workspaceId);
        if(!authorizationService.isPageOwner(userWorkspace,pageId)) throw new AccessDeniedException("");
        Page updatedPage = notesService.removeUDB(pageId,udbId);
        Map<String, Object> response = new HashMap<>();
        response.put("newPage",updatedPage);
        log.info("user:"+ userName + "in his workspace with id :" +workspaceId + "deleted from page with id :"+ pageId+ "a udb with id" + udbId);
        return ResponseEntity.ok(response);
    }
}
