package com.example.flashcard_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flashcard_app.model.Notebooks;

public interface NotebooksRepository extends JpaRepository<Notebooks, UUID> {
    public List<Notebooks> findByLikedTrue();
    public List<Notebooks> findByGroupId(UUID groupId);
}
