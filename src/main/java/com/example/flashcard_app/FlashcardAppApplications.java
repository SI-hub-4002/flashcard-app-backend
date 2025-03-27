package com.example.flashcard_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.flashcard_app.controller.FlashcardsController;
import com.example.flashcard_app.controller.GroupController;
import com.example.flashcard_app.controller.NotebooksController;
import com.example.flashcard_app.controller.UsersController;
import com.example.flashcard_app.model.Flashcards;
import com.example.flashcard_app.model.Groups;
import com.example.flashcard_app.model.Notebooks;
import com.example.flashcard_app.model.Users;

import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@EnableJpaRepositories("com.example.flashcard_app.repository")
@EntityScan("com.example.flashcard_app.model") 
@ComponentScan(basePackages = {
		"com.example.flashcard_app.controller",
		"com.example.flashcard_app.service",
		"com.example.flashcard_app.repository"
})
public class FlashcardAppApplications implements RequestHandler<Map<String, Object>, Object> {

	@SuppressWarnings("unchecked")
	@Override
	public Object handleRequest(Map<String, Object> input, Context context) {
		String[] args = new String[0];

		try (ConfigurableApplicationContext ctx = SpringApplication.run(FlashcardAppApplications.class, args)) {

			// FlashcardsController, GroupController, NotebooksController, UsersControllerを取得
			FlashcardsController flashcardsController = ctx.getBean(FlashcardsController.class);
			GroupController groupController = ctx.getBean(GroupController.class);
			NotebooksController notebooksController = ctx.getBean(NotebooksController.class);
			UsersController usersController = ctx.getBean(UsersController.class);

			String httpMethod = (String) input.get("httpMethod");
			String path = (String) input.get("path");
			Map<String, Object> queryParams = (Map<String, Object>) input.get("queryStringParameters");
			Map<String, Object> body = (Map<String, Object>) input.get("body");

			// POST メソッドの処理 (Flashcards, Groups, Notebooks, Users)
			if ("POST".equalsIgnoreCase(httpMethod)) {
				if (path.startsWith("/api/flashcards")) {
					Flashcards flashcards = parseFlashcard(body);
					UUID notebookId = UUID.fromString((String) queryParams.get("notebookId"));
					return flashcardsController.saveFlashcard(flashcards, notebookId);
				} else if (path.startsWith("/api/groups")) {
					Groups groups = parseGroup(body);
					return groupController.saveGroup(groups);
				} else if (path.startsWith("/api/notebooks")) {
					Notebooks notebooks = parseNotebook(body);
					UUID groupId = UUID.fromString((String) queryParams.get("groupId"));
					return notebooksController.saveNotebook(notebooks, groupId);
				} else if (path.startsWith("/api/users")) {
					Users users = parseUser(body);
					return usersController.saveUser(users);
				}
			}
			// GET メソッドの処理 (Flashcards, Groups, Notebooks, Users)
			else if ("GET".equalsIgnoreCase(httpMethod)) {
				if (path.matches("/api/flashcards/[a-fA-F0-9\\-]+")) {
					UUID flashcardId = UUID.fromString(path.substring("/api/flashcards/".length()));
					return flashcardsController.getFlashcard(flashcardId);
				} else if (path.equals("/api/flashcards")) {
					UUID notebookId = UUID.fromString((String) queryParams.get("notebookId"));
					return flashcardsController.getAllFlashcards(notebookId);
				} else if (path.matches("/api/groups/[a-fA-F0-9\\-]+")) {
					UUID groupId = UUID.fromString(path.substring("/api/groups/".length()));
					return groupController.getGroups(groupId);
				} else if (path.equals("/api/groups")) {
					return groupController.getAllGroups();
				} else if (path.matches("/api/notebooks/[a-fA-F0-9\\-]+")) {
					UUID notebookId = UUID.fromString(path.substring("/api/notebooks/".length()));
					return notebooksController.getNotebook(notebookId);
				} else if (path.equals("/api/notebooks")) {
					UUID groupId = UUID.fromString((String) queryParams.get("groupId"));
					return notebooksController.getAllNotebooks(groupId);
				} else if (path.equals("/api/notebooks/liked")) {
					return notebooksController.getAllLikedNotebooks();
				}
			}
			// PUT メソッドの処理 (Flashcards, Groups, Notebooks)
			else if ("PUT".equalsIgnoreCase(httpMethod)) {
				if (path.startsWith("/api/flashcards")) {
					UUID flashcardId = UUID.fromString(path.substring("/api/flashcards/".length()));
					Flashcards flashcardDetails = parseFlashcard(body);
					return flashcardsController.updateFlashcard(flashcardId, flashcardDetails);
				} else if (path.startsWith("/api/groups")) {
					UUID groupId = UUID.fromString(path.substring("/api/groups/".length()));
					Groups groupDetails = parseGroup(body);
					return groupController.updateGroups(groupId, groupDetails);
				} else if (path.startsWith("/api/notebooks")) {
					UUID notebookId = UUID.fromString(path.substring("/api/notebooks/".length()));
					Notebooks notebookDetails = parseNotebook(body);
					return notebooksController.updateNotebook(notebookId, notebookDetails);
				}
			}
			// PATCH メソッドの処理 (Flashcards, Notebooks)
			else if ("PATCH".equalsIgnoreCase(httpMethod)) {
				if (path.startsWith("/api/flashcards")) {
					UUID flashcardId = UUID.fromString(path.substring("/api/flashcards/".length()));
					Flashcards flashcardDetails = parseFlashcard(body);
					return flashcardsController.updateFlashcard(flashcardId, flashcardDetails);
				} else if (path.startsWith("/api/notebooks")) {
					UUID notebookId = UUID.fromString(path.substring("/api/notebooks/".length()));
					Notebooks notebookDetails = parseNotebook(body);
					return notebooksController.updateNotebookPartial(notebookId, notebookDetails);
				}
			}
			// DELETE メソッドの処理 (Flashcards, Groups, Notebooks)
			else if ("DELETE".equalsIgnoreCase(httpMethod)) {
				if (path.startsWith("/api/flashcards")) {
					UUID flashcardId = UUID.fromString(path.substring("/api/flashcards/".length()));
					return flashcardsController.deleteFlashcard(flashcardId);
				} else if (path.startsWith("/api/groups")) {
					UUID groupId = UUID.fromString(path.substring("/api/groups/".length()));
					return groupController.deleteGroup(groupId);
				} else if (path.startsWith("/api/notebooks")) {
					UUID notebookId = UUID.fromString(path.substring("/api/notebooks/".length()));
					return notebooksController.deleteNotebook(notebookId);
				}
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error processing request: " + e.getMessage());
		}
		return ResponseEntity.badRequest().body("Invalid request");
	}

	private Flashcards parseFlashcard(Map<String, Object> body) {
		Flashcards flashcards = new Flashcards();
		flashcards.setNotebookId(UUID.fromString((String) body.get("notebookId")));
		flashcards.setFront_text((String) body.get("front_text"));
		flashcards.setBack_text((String) body.get("back_text"));
		flashcards.setDetail((String) body.get("detail"));
		flashcards.setBookmarked((Boolean) body.get("bookmarked"));
		return flashcards;
	}

	private Groups parseGroup(Map<String, Object> body) {
		Groups group = new Groups();
		group.setSub(UUID.fromString((String) body.get("sub")));
		group.setGroupname((String) body.get("groupname"));
		group.setDescription((String) body.get("description"));
		group.setNotebooks((Integer) body.get("notebooks"));
		group.setFlashcards((Integer) body.get("flashcards"));
		return group;
	}

	private Notebooks parseNotebook(Map<String, Object> body) {
		Notebooks notebooks = new Notebooks();
		notebooks.setGroupId(UUID.fromString((String) body.get("groupId")));
		notebooks.setTitle((String) body.get("title"));
		notebooks.setDescription((String) body.get("description"));
		notebooks.setLiked((Boolean) body.get("liked"));
		return notebooks;
	}

	private Users parseUser(Map<String, Object> body) {
		Users users = new Users();
		users.setSub(UUID.fromString((String) body.get("sub")));
		return users;
	}

	public static void main(String[] args) {
		SpringApplication.run(FlashcardAppApplications.class, args);
	}
}
