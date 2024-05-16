package com.notionreplica.udbs.controller;

import com.notionreplica.udbs.entities.UDBDataTable;
import com.notionreplica.udbs.services.UDBDataTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/{userId}/workspace")
public class UDBDataTableController {
    @Autowired
    private UDBDataTableService udbDataTableService;

    @GetMapping("/udbTable/{UDBid}")
    public ResponseEntity<Map<String,Object>> getUDBDataTable(@PathVariable("UDBid") String id) throws Exception {
        UDBDataTable udbDataTable = udbDataTableService.getUDBDataTable(id);
        Map<String, Object> response = new HashMap<>();
        response.put("UDB Data Table", udbDataTable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createUdbTable")
    public ResponseEntity<Map<String,Object>> createUDBDataTable(@RequestBody Map<String,String> reqBody) throws Exception {
        UDBDataTable udbDataTable = udbDataTableService.createUDBDataTable(reqBody.get("title"));
        Map<String, Object> response = new HashMap<>();
        response.put("UDB Data Table", udbDataTable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateUdbTable/{UDBid}")
    public ResponseEntity<Map<String,Object>> updateUDBDataTable(@PathVariable("UDBid") String id, @RequestBody Map<String,String> reqBody) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try{
            UDBDataTable udbDataTable = udbDataTableService.getUDBDataTable(id);
            if (reqBody.containsKey("title") && reqBody.get("title") != null) {
                udbDataTable = udbDataTableService.updateUDBDataTable(id, reqBody.get("title"));
            }
            if (reqBody.containsKey("propertyID") && reqBody.get("propertyID") != null) {
                udbDataTable= udbDataTableService.addPropertyToUDBDataTable(id, reqBody.get("propertyID"));
            }
            //if(reqBody.containsKey("udbpagesID") && reqBody.get("udbpagesID") != null){}

            response.put("UDB Data Table", udbDataTable);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            //Edit this
            return ResponseEntity.ok(response);
        }

    }

    //@PutMapping("/updateUdbTable/{UDBid}/property/{propertyID}")

    //@DeleteMapping("/updateUdbTable/{UDBid}/property/{propertyID}")


    @DeleteMapping("/deleteUdbTable/{UDBid}")
    public ResponseEntity<String> deleteUDBDataTable(@PathVariable("UDBid") String id) throws Exception {
        udbDataTableService.deleteUDBDataTable(id);
        return ResponseEntity.ok("Deleted UDB Data Table");
    }

}
