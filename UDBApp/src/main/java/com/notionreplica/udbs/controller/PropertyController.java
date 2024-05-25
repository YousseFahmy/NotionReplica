package com.notionreplica.udbs.controller;

import com.notionreplica.udbs.entities.Properties;
import com.notionreplica.udbs.entities.PropertyType;
import com.notionreplica.udbs.services.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/udb/{userId}/workspace/{workspaceId}/notes")
public class PropertyController {

    @Autowired
    PropertyService propertyService;
    Logger log = LoggerFactory.getLogger(PropertyController.class);

    @GetMapping("/property/{propertyID}")
    public ResponseEntity<Map<String,Object>> getProperty(@PathVariable("propertyID") String propertyID) throws Exception {
        Properties property = propertyService.getProperty(propertyID);
        Map<String, Object> response = new HashMap<>();
        response.put("Property", property);
        log.info("user accessed property with id :" + propertyID);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createProperty")
    public ResponseEntity<Map<String,Object>> createProperty(@RequestBody Map<String,Object> propertyData) throws Exception {
        String propertyType = (String) propertyData.get("propertyType");
        String title = (String) propertyData.get("title");
        Properties property = propertyService.createProperty(PropertyType.valueOf(propertyType.toUpperCase()), title);
        Map<String, Object> response = new HashMap<>();
        response.put("Property", property);
        log.info("user created property with id :" + property.getPropertyID() + " and title :" + title);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/property/{propertyID}")
    public ResponseEntity<String> deleteProperty(@PathVariable("propertyID") String propertyID) throws Exception {
        propertyService.deleteProperty(propertyID);
        log.info("user deleted property with id :" + propertyID);
        return ResponseEntity.ok("Property " + propertyID + " deleted");
    }

    @PutMapping("/property/{propertyID}")
    public ResponseEntity<Map<String,Object>> updateProperty(@PathVariable("propertyID") String propertyID,
                                                             @RequestBody Map<String,Object> propertyData) throws Exception {
        String title = propertyService.getProperty(propertyID).getTitle();
        PropertyType propertyType = propertyService.getProperty(propertyID).getType();
        if (propertyData.containsKey("title") && propertyData.get("title") != null) {
          title = (String) propertyData.get("title");
        }
        if (propertyData.containsKey("propertyType") && propertyData.get("propertyType") != null) {
            String type = (String) propertyData.get("propertyType");
            propertyType = PropertyType.valueOf(type.toUpperCase());
        }

        Properties property = propertyService.updateProperty(propertyID, propertyType, title);
        Map<String, Object> response = new HashMap<>();
        response.put("Property", property);
        log.info("user updated property with id :" + propertyID + " and title :" + title);
        return ResponseEntity.ok(response);
    }
}
