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

import com.example.flashcard_app.model.Flashcards;
import com.example.flashcard_app.service.FlashcardsService;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardsController {
    
    @Autowired
    FlashcardsService flashcardsService;

    @PostMapping
    public ResponseEntity<String> saveFlashcard(@RequestBody Flashcards flashcards, @RequestParam("notebookId") UUID notebookId) {
        flashcards.setNotebookId(notebookId);

        flashcardsService.insert(flashcards);
        return ResponseEntity.status(HttpStatus.CREATED).body("Flashcard saved successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Flashcards>> getAllFlashcards(UUID notebookId) {
        List<Flashcards> flashcards = flashcardsService.findByNotebookId(notebookId);
        return ResponseEntity.ok(flashcards);
    }

    @GetMapping("/{flashcardId}")
    public ResponseEntity<Optional<Flashcards>> getFlashcard(@PathVariable UUID flashcardId) {
        Optional<Flashcards> flashcard = flashcardsService.findById(flashcardId);
        return ResponseEntity.ok(flashcard);
    }

    @PutMapping("/{flashcardId}")
    public ResponseEntity<Flashcards> updateFlashcard(@PathVariable UUID flashcardId, @RequestBody Flashcards flashcardDetails) {
        Flashcards updatedFlashcard = flashcardsService.update(flashcardId, flashcardDetails);
        return ResponseEntity.ok(updatedFlashcard);
    }

    @PatchMapping("/{flashcardId}")
    public ResponseEntity<Flashcards> updateFlashcardPartial(@PathVariable UUID flashcardId, @RequestBody Flashcards flashcardDetails) {
        Flashcards updatedFlashcard = flashcardsService.patch(flashcardId, flashcardDetails);
        return ResponseEntity.ok(updatedFlashcard);
    }

    @DeleteMapping("/{flashcardId}")
    public ResponseEntity<String> deleteFlashcard(@PathVariable UUID flashcardId) {
        flashcardsService.deleteById(flashcardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Flashcard deleted successfully!");
    }
}
