package com.example.jblog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jblog.model.Vote;

@Repository
public interface VoteRepo extends JpaRepository<Vote, String> {
    // List<Post> findTopRatedPosts(User user);
    // List<Post> findTopRatedPosts(Group group);
}
