package com.example.flashcard_app.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.flashcard_app.model.Notebooks;
import com.example.flashcard_app.repository.NotebooksRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NotebooksService {

    private NotebooksRepository notebooksRepository;

    @Autowired
    public NotebooksService(NotebooksRepository notebooksRepository){
		this.notebooksRepository = notebooksRepository;
	}

    public void insert(Notebooks notebooks) {

        if (notebooks.getCreated_at() == null) {
            notebooks.setCreated_at(new Timestamp(System.currentTimeMillis()));
        }

        if (notebooks.getUpdated_at() == null) {
            notebooks.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        }

        notebooksRepository.save(notebooks);
    }

    public List<Notebooks> findByGroupId(UUID groupId) {
        return notebooksRepository.findByGroupId(groupId);
    }

    public List<Notebooks> findByLiked() {
        return notebooksRepository.findByLikedTrue();
    }

    public Optional<Notebooks> findById(UUID notebookId) {
        return notebooksRepository.findById(notebookId);
    }

    public Notebooks update(UUID notebookId, Notebooks notebookDetails) {

        Optional<Notebooks> existingNotebook = notebooksRepository.findById(notebookId);

        if (existingNotebook.isPresent()) {
            Notebooks notebookToUpdate = existingNotebook.get();

            notebookToUpdate.setGroupId(notebookDetails.getGroupId());
            notebookToUpdate.setTitle(notebookDetails.getTitle());
            notebookToUpdate.setDescription(notebookDetails.getDescription());
            notebookToUpdate.setLiked(notebookDetails.getLiked());

            return notebooksRepository.save(notebookToUpdate);
        } else {

            throw new EntityNotFoundException("Note not found with id " + notebookId);
        }
    }

    public Notebooks patch(UUID notebookId, Notebooks notebookDetails) {
        Optional<Notebooks> existingNotebook = notebooksRepository.findById(notebookId);
    
        if (existingNotebook.isPresent()) {
            Notebooks notebookToUpdate = existingNotebook.get();
    
            if (notebookDetails.getGroupId() != null) {
                notebookToUpdate.setGroupId(notebookDetails.getGroupId());
            }
            if (notebookDetails.getTitle() != null) {
                notebookToUpdate.setTitle(notebookDetails.getTitle());
            }
            if (notebookDetails.getDescription() != null) {
                notebookToUpdate.setDescription(notebookDetails.getDescription());
            }
            if (notebookDetails.getLiked() != null) {
                notebookToUpdate.setLiked(notebookDetails.getLiked());
            }
    
            return notebooksRepository.save(notebookToUpdate);
        } else {
            throw new EntityNotFoundException("Flashcard not found with id " + notebookId);
        }
    }

    public void deleteById(UUID notebookId) {
        notebooksRepository.deleteById(notebookId);
    }
}
