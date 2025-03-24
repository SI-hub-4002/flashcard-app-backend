package com.example.flashcard_app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flashcard_app.model.Groups;

public interface GroupsRepository extends JpaRepository<Groups, UUID> {
}
