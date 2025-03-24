package com.example.flashcard_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flashcard_app.model.Users;
import com.example.flashcard_app.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    
    @Autowired
    UsersService usersService;

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody Users users) {
        usersService.insert(users);
        return ResponseEntity.status(HttpStatus.CREATED).body("User saved successfully!");
    }
}

