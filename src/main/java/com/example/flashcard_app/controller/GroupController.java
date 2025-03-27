package com.example.flashcard_app.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.flashcard_app.model.Groups;
import com.example.flashcard_app.service.GroupsService;

import lombok.Data;

@RestController
@Data
@RequestMapping("/api/groups")
public class GroupController {

    private GroupsService groupsService;

    private Object input;
    private Context context;

    @Autowired
    public GroupController(GroupsService groupsService){
		this.groupsService = groupsService;
	}

    @PostMapping
    public ResponseEntity<String> saveGroup(@RequestBody Groups groups) {
        groupsService.insert(groups);
        return ResponseEntity.status(HttpStatus.CREATED).body("Group saved successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Groups>> getAllGroups() {
        List<Groups> groups = groupsService.findAll();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Optional<Groups>> getGroups(@PathVariable UUID groupId) {
        Optional<Groups> groups = groupsService.findById(groupId);
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Groups> updateGroups(@PathVariable UUID groupId, @RequestBody Groups groupsDetails) {
        Groups updatedGroup = groupsService.update(groupId, groupsDetails);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable UUID groupId) {
        groupsService.deleteById(groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Group deleted successfully!");
    }
}
