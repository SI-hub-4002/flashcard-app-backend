package com.example.flashcard_app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.flashcard_app.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
}
