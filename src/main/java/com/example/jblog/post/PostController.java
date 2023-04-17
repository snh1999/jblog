package com.example.jblog.post;

import com.example.jblog.model.Comment;
import com.example.jblog.model.Post;
import com.example.jblog.post.dto.PostDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/post")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public Post createPost(@RequestBody PostDto postDto) {
        return postService.create(postDto);
    }

    @GetMapping()
    public List<Post> findAll() {
        System.out.println("Asdfasfdsd");

        return postService.findAll();
    }

    @GetMapping("{id}")
    public Post findOne(@PathVariable String id) {
        return postService.findPost(id);
    }

    @PatchMapping("{id}")
    public String update(@PathVariable String id, @RequestBody PostDto postDto) {
        return postService.update(id, postDto);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable String id) {
        return postService.delete(id);
    }
    // get all by user
    // get all by group

}
