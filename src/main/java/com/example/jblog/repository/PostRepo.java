package com.example.jblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jblog.model.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, String> {
    // List<Post> findAllByGroup(Group group);
    // List<Post> findALLByAuthor(User author);
}
