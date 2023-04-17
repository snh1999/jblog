package com.example.jblog.repository;


import com.example.jblog.model.Post;
import com.example.jblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jblog.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, String> {
    List<Comment>findByAuthor(User author);
    List<Comment> findByParentCommentIsNullAndParentPost(Post post);

}
