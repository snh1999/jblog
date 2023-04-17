package com.example.jblog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jblog.model.Group;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group, String> {
    List<Group> findByName(String Name);
    Group findByUrl(String url);
    Optional<Group> findByIdOrUrl(String nameOrUrl, String nameOrUrlDuplicate);


}
