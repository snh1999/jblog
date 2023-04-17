package com.example.jblog.post.vote;

import com.example.jblog.auth.AuthService;
import com.example.jblog.filter.GenericException;
import com.example.jblog.model.Post;
import com.example.jblog.model.User;
import com.example.jblog.model.Vote;
import com.example.jblog.model.enums.VoteType;
import com.example.jblog.post.PostService;
import com.example.jblog.post.vote.dto.VoteDto;
import com.example.jblog.repository.VoteRepo;
import com.example.jblog.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final PostService postService;
    private final AuthService authService;
    private final VoteRepo voteRepo;
    public int vote(VoteDto voteDto) {
        Post post = postService.findPost(voteDto.getPostId());
        User user = authService.getCurrentUser();
        var vote = voteRepo.findByUserAndPost(user, post);
        if(vote.isPresent() && vote.get().getVoteType().equals(voteDto.getVoteType())) {
            return 0;
        }
        else if (vote.isPresent()) {
            // vote is present but opposite button pressed
            deleteVote(vote.get());
            updateVoteCount(voteDto.getVoteType(), post);
            return voteDto.getVoteType()== VoteType.UPVOTE ? 1 : -1;
        }
        Vote newVote = Vote.builder()
                .user(user)
                .post(post)
                .voteType(voteDto.getVoteType())
                .build();
        updateVoteCount(voteDto.getVoteType(), post);
        voteRepo.save(newVote);
        return voteDto.getVoteType()== VoteType.UPVOTE ? 1 : -1;
        // save vote and post
    }
    public void deleteVote(Vote vote) {
        voteRepo.delete(vote);
    }
    // delete vote
    private void updateVoteCount(VoteType voteType, Post post) {
        if(VoteType.UPVOTE.equals(voteType)) {
            postService.updateAndSaveVoteCount(post, 1);
        }
        else if(VoteType.DOWNVOTE.equals(voteType)) {
            postService.updateAndSaveVoteCount(post, -1);

        }
    }

}
