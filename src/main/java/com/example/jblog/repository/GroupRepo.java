package com.example.jblog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jblog.model.Group;

@Repository
public interface GroupRepo extends JpaRepository<Group, String> {
    // Optional<Group> findbyName(String name);
}
