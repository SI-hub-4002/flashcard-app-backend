package com.example.flashcard_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flashcard_app.model.Flashcards;

public interface FlashcardsRepository extends JpaRepository<Flashcards, UUID> {
    public List<Flashcards> findByNotebookId(UUID notebookId);
}
