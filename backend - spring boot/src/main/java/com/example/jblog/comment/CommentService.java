package com.example.jblog.comment;

import com.example.jblog.auth.AuthService;
import com.example.jblog.comment.dto.CommentDto;
import com.example.jblog.filter.ResourceNotFoundException;
import com.example.jblog.filter.dto.CustomResponse;
import com.example.jblog.model.Comment;
import com.example.jblog.model.Post;
import com.example.jblog.model.User;
import com.example.jblog.post.PostService;
import com.example.jblog.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final AuthService authService;
    private final PostService postService;
    private final CommentRepo commentRepo;

    public List<Comment> findAllByPostId(String postId) {
        Post post = postService.findPost(postId);
        return  commentRepo.findByParentCommentIsNullAndParentPost(post);
    }

    public Comment findOne(String id) {
        return commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
    public Comment create(CommentDto commentDto) {
        return createAndSave(commentDto, new Comment());
    }

    public Comment create(String commentId, CommentDto commentDto) {
        Comment parentComment = findOne(commentId);
        Comment comment = Comment.builder().parentComment(parentComment).build();
        return createAndSave(commentDto, comment);
    }
    private Comment createAndSave(CommentDto commentDto, Comment comment) {
        User user = authService.getCurrentUser();
        Post post = postService.findPost(commentDto.getPostId());
        comment.setText(commentDto.getText());
        comment.setAuthor(user);
        comment.setParentPost(post);

        return commentRepo.save(comment);
//        return getResponseObj("Comment created", comment);
    }

    public CustomResponse update(String commentId, CommentDto commentDto) {
        Comment comment = findOne(commentId);
        comment.setText(commentDto.getText());
        commentRepo.save(comment);
        return getResponseObj("Update Successful", comment);
    }

    public CustomResponse delete(String commentId) {
        commentRepo.deleteById(commentId);
        return getResponseObj("Deleted Successfully", null);
    }

    private CustomResponse getResponseObj(String msg, Comment comment) {
        return CustomResponse.builder()
                .detail(msg)
                .redirectUrl(comment != null? comment.getParentPost().getId(): "")
                .build();
    }
}
