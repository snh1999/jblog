package com.example.jblog.repository;


import com.example.jblog.model.Post;
import com.example.jblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jblog.model.Vote;

import java.util.Optional;

@Repository
public interface VoteRepo extends JpaRepository<Vote, String> {
    // List<Post> findTopRatedPosts(User user);
    // List<Post> findTopRatedPosts(Group group);
    Optional<Vote> findByUserAndPost(User user, Post post);
}
