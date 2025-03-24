package com.example.flashcard_app.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.flashcard_app.model.Notebooks;
import com.example.flashcard_app.service.NotebooksService;

@RestController
@RequestMapping("/api/notebooks")
public class NotebooksController {
    
    @Autowired
    NotebooksService notebooksService;

    @PostMapping
    public ResponseEntity<String> saveNotebook(@RequestBody Notebooks notebooks, @RequestParam("groupId") UUID groupId) {
        notebooks.setGroupId(groupId);
        
        notebooksService.insert(notebooks);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notebook saved successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Notebooks>> getAllNotebooks(@RequestParam("groupId") UUID groupId) {
        List<Notebooks> notebooks = notebooksService.findByGroupId(groupId);
        return ResponseEntity.ok(notebooks);
    }

    @GetMapping("/liked")
    public ResponseEntity<List<Notebooks>> getAllLikedNotebooks() {
        List<Notebooks> notebooks = notebooksService.findByLiked();
        return ResponseEntity.ok(notebooks);
    }

    @GetMapping("/{notebookId}")
    public ResponseEntity<Optional<Notebooks>> getNotebook(@PathVariable UUID notebookId) {
        Optional<Notebooks> notebook = notebooksService.findById(notebookId);
        return ResponseEntity.ok(notebook);
    }

    @PutMapping("/{notebookId}")
    public ResponseEntity<Notebooks> updateNotebook(@PathVariable UUID notebookId, @RequestBody Notebooks notebookDetails) {
        Notebooks updatedNotebook = notebooksService.update(notebookId, notebookDetails);
        return ResponseEntity.ok(updatedNotebook);
    }

    @PatchMapping("/{notebookId}")
    public ResponseEntity<Notebooks> updateNotebookPartial (@PathVariable UUID notebookId, @RequestBody Notebooks notebookDetails) {
        Notebooks updatedNotebook = notebooksService.patch(notebookId, notebookDetails);
        return ResponseEntity.ok(updatedNotebook);
    }

    @DeleteMapping("/{notebookId}")
    public ResponseEntity<String> deleteNotebook(@PathVariable UUID notebookId) {
        notebooksService.deleteById(notebookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Notebook deleted successfully!");
    }
}
