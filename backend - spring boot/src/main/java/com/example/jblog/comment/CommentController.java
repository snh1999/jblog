package com.example.jblog.comment;

import com.example.jblog.comment.dto.CommentDto;
import com.example.jblog.filter.dto.CustomResponse;
import com.example.jblog.model.Comment;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/comment")
public class CommentController {
    private CommentService commentService;




    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> findAllByPost(@PathVariable String postId) {
        return ResponseEntity.ok(commentService.findAllByPostId(postId));
    }

    @PostMapping()
    public ResponseEntity<Comment> create(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.create(commentDto));
    }

    @PatchMapping("{commentId}")
    public ResponseEntity<CustomResponse> update(@PathVariable String commentId, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.update(commentId, commentDto));
    }

    @PostMapping("{commentId}")
    public ResponseEntity<Comment> createReply(@PathVariable String commentId, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.create(commentId, commentDto));
    }

    @GetMapping("{commentId}")
    public ResponseEntity<Comment> findOne(@PathVariable String commentId) {
        return ResponseEntity.ok(commentService.findOne(commentId));
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<CustomResponse> delete(@PathVariable String commentId) {
        return ResponseEntity.ok(commentService.delete(commentId));
    }

}
