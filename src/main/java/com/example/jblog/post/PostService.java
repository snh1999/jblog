package com.example.jblog.post;

import com.example.jblog.auth.AuthService;
import com.example.jblog.filter.GenericException;
import com.example.jblog.filter.ResourceNotFoundException;
import com.example.jblog.group.GroupService;
import com.example.jblog.model.Comment;
import com.example.jblog.model.Group;
import com.example.jblog.model.Post;
import com.example.jblog.model.User;
import com.example.jblog.post.dto.PostDto;
import com.example.jblog.repository.PostRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final AuthService authService;
    private final GroupService groupService;

    @Transactional
    public Post create(PostDto postDto) {
        User user = authService.getCurrentUser();
        Post post = Post.builder()
                .title(postDto.getTitle())
                .author(user)
                .description(postDto.getDescription())
                .summary(postDto.getSummary())
                .url(postDto.getUrl())
                .category(postDto.getCategory())
                .build();
        if (!postDto.getGroupId().isBlank()) {
            Group group = groupService.findById(postDto.getGroupId());
            post.setGroup(group);
        }

        return postRepo.save(post);
    }

    public List<Post> findAll() {
        return postRepo.findAll();
    }

    public Post findPost(String id) {
        return findByIdOrUrl(id);
    }

    private Post findByIdOrUrl(String idOrUrl) {
        return postRepo.findByIdOrUrl(idOrUrl, idOrUrl).orElseThrow(() -> new ResourceNotFoundException("Post Not found"));
    }

    public String update(String id, PostDto postDto)  {
        Post post = findPost(id);

        this.checkAuthorization(post.getAuthor().getId());

        if (!postDto.getTitle().isBlank()) post.setTitle(postDto.getTitle());
        if (!postDto.getDescription().isBlank()) post.setDescription(postDto.getDescription());
        if (!postDto.getSummary().isBlank()) post.setSummary(postDto.getSummary());
        if (postDto.getUrl()!= null && !postDto.getUrl().isBlank()) post.setUrl(postDto.getSummary());
        if (postDto.getCategory() != null) post.setCategory(postDto.getCategory());

        postRepo.save(post);
        return "Updated Successfully";
    }

    public String delete(String id)  {
        Post post = this.findPost(id);
        this.checkAuthorization(post.getAuthor().getId());

        postRepo.delete(post);
        return "Deleted Successfully";

    }

    private void checkAuthorization(String authorId) {
        String currentUser = authService.getCurrentUser().getId();
        if(!currentUser.equals(authorId)) throw new GenericException("Unauthorized, you can not perform this operation");
    }

    public void updateAndSaveVoteCount(Post post, int voteAdd) {
        post.setVoteCount(post.getVoteCount() + voteAdd);
        postRepo.save(post);
    }
}
