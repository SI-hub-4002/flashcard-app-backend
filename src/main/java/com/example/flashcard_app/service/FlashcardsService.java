package com.example.flashcard_app.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.flashcard_app.model.Flashcards;
import com.example.flashcard_app.repository.FlashcardsRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

@Service
@Data
public class FlashcardsService {

    private FlashcardsRepository flashcardRepository;

    @Autowired
    public FlashcardsService(FlashcardsRepository flashcardRepository){
		this.flashcardRepository = flashcardRepository;
	}

    public void insert(Flashcards flashcards) {
        if (flashcards.getCreated_at() == null) {
            flashcards.setCreated_at(new Timestamp(System.currentTimeMillis()));
        }

        if (flashcards.getUpdated_at() == null) {
            flashcards.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        }

        flashcardRepository.save(flashcards);
    }

    public List<Flashcards> findByNotebookId(UUID notebookId) {
        return flashcardRepository.findByNotebookId(notebookId);
    }

    public Optional<Flashcards> findById(UUID flashcardId) {
        return flashcardRepository.findById(flashcardId);
    }

    public Flashcards update(UUID flashcardId, Flashcards flashcardDitails) {
        Optional<Flashcards> exitingFlashcard = flashcardRepository.findById(flashcardId);

        if (exitingFlashcard.isPresent()) {
            Flashcards flashcardToUpdate = exitingFlashcard.get();

            flashcardToUpdate.setFront_text(flashcardDitails.getFront_text());
            flashcardToUpdate.setBack_text(flashcardDitails.getBack_text());
            flashcardToUpdate.setDetail(flashcardDitails.getDetail());
            flashcardToUpdate.setBookmarked(flashcardDitails.getBookmarked());

            return flashcardRepository.save(flashcardToUpdate);
        } else {

            throw new EntityNotFoundException("Flashcard not found with id " + flashcardId);
        }
    }

    public Flashcards patch(UUID flashcardId, Flashcards flashcardDetails) {
        Optional<Flashcards> existingFlashcard = flashcardRepository.findById(flashcardId);
    
        if (existingFlashcard.isPresent()) {
            Flashcards flashcardToUpdate = existingFlashcard.get();
    
            if (flashcardDetails.getFront_text() != null) {
                flashcardToUpdate.setFront_text(flashcardDetails.getFront_text());
            }
            if (flashcardDetails.getBack_text() != null) {
                flashcardToUpdate.setBack_text(flashcardDetails.getBack_text());
            }
            if (flashcardDetails.getDetail() != null) {
                flashcardToUpdate.setDetail(flashcardDetails.getDetail());
            }
            if (flashcardDetails.getBookmarked() != null) {
                flashcardToUpdate.setBookmarked(flashcardDetails.getBookmarked());
            }
    
            return flashcardRepository.save(flashcardToUpdate);
        } else {
            throw new EntityNotFoundException("Flashcard not found with id " + flashcardId);
        }
    }
    

    public void deleteById(UUID flashcardId) {
        flashcardRepository.deleteById(flashcardId);
    }
}
