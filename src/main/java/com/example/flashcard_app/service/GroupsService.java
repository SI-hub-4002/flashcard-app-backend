package com.example.flashcard_app.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.flashcard_app.model.Groups;
import com.example.flashcard_app.repository.GroupsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GroupsService {

    private GroupsRepository groupsRepository;

    @Autowired
    public GroupsService(GroupsRepository groupsRepository){
		this.groupsRepository = groupsRepository;
	}

    public void insert(Groups groups) {
        if (groups.getCreated_at() == null) {
            groups.setCreated_at(new Timestamp(System.currentTimeMillis()));
        }

        if (groups.getUpdated_at() == null) {
            groups.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        }

        groupsRepository.save(groups);
    }

    public List<Groups> findAll() {
        return groupsRepository.findAll();
    }

    public Optional<Groups> findById(UUID groupId) {
        return groupsRepository.findById(groupId);
    }

    public Groups update(UUID groupId, Groups groupDetails) {
        Optional<Groups> existingGroup = groupsRepository.findById(groupId);

        if (existingGroup.isPresent()) {
            Groups groupToUpdate = existingGroup.get();

            groupToUpdate.setGroupname(groupDetails.getGroupname());
            groupToUpdate.setDescription(groupDetails.getDescription());

            return groupsRepository.save(groupToUpdate);
        } else {
            throw new EntityNotFoundException("Group not found with id " + groupId);
        }
    }

    public void deleteById(UUID groupId) {
        groupsRepository.deleteById(groupId);
    }
}
