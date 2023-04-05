package com.example.jblog.post;

import com.example.jblog.auth.AuthService;
import com.example.jblog.filter.GenericException;
import com.example.jblog.model.Post;
import com.example.jblog.model.User;
import com.example.jblog.post.dto.PostDto;
import com.example.jblog.repository.PostRepo;
import jakarta.transaction.Transactional;

import java.util.List;

public class PostService {

    private PostRepo PostRepo;
    private AuthService authService;

    @Transactional
    public Post create(PostDto postDto) {
        User user = authService.getCurrentUser();
        Post post = Post.builder()
                .title(postDto.getTitle())
                .author(user)
                .description(postDto.getDescription())

                .build();

//        if (PostDto.getUrl().isBlank()) Post.setUrl(Post.getId());
//        else Post.setUrl(PostDto.getUrl());
//
//        PostRepo.save(Post);
//        return Post;
        return null;
    }

    public List<Post> findAll() {
        return PostRepo.findAll();
    }

    public Post findById(String id) {
        return null;
//        return PostRepo.findbyIdOrUrl(id, id).orElseThrow(() -> new ResourceAccessException("Not found"));
    }

    public String update(String id, PostDto postDto)  {

//        Post Post = this.findById(id);
//        this.checkAuthorization(Post.getOwner());
//
//        if (!PostDto.getName().isBlank()) Post.setName(PostDto.getName());
//        if (!PostDto.getDescription().isBlank()) Post.setDescription(PostDto.getDescription());
//        if (!PostDto.getUrl().isBlank()) Post.setName(PostDto.getUrl());

//        PostRepo.save(Post);
        return "Updated Successfully";
    }

    public String delete(String id)  {
        Post Post = this.findById(id);
//        this.checkAuthorization(Post.getOwner());

//        PostRepo.delete(Post);
        return "Deleted Successfully";

    }

    private void checkAuthorization(User owner) {
        String currentUser = authService.getCurrentUser().getId();
        if(!currentUser.equals(owner.getId())) throw new GenericException("Unauthorized");
    }
}
