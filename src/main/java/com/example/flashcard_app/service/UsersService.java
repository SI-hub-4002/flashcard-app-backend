package com.example.flashcard_app.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.flashcard_app.model.Users;
import com.example.flashcard_app.repository.UsersRepository;

@Service
public class UsersService {

  private UsersRepository usersRepository;

  @Autowired
  public UsersService(UsersRepository usersRepository){
		this.usersRepository = usersRepository;
	}

  public void insert(Users users) {

    if (users.getCreated_at() == null) {
      users.setCreated_at(new Timestamp(System.currentTimeMillis()));
    }

    if (users.getUpdated_at() == null) {
      users.setUpdated_at(new Timestamp(System.currentTimeMillis()));
    }

    usersRepository.save(users);
  }
}
