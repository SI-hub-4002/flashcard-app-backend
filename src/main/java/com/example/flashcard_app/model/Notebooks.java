package com.example.flashcard_app.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "notebooks")
public class Notebooks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notebook_id")
	private UUID notebookId;
	@Column(name = "sub")
	private UUID sub;
	@Column(name = "group_id")
	private UUID groupId;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "liked")
	private Boolean liked;
	@Column(name = "flashcards")
	private Integer flashcards;
	@Column(name = "created_at")
	private Timestamp created_at;
	@Column(name = "updated_at")
	private Timestamp updated_at;
}
