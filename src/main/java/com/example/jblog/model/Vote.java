package com.example.jblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = UUID)
    private String voteId;
    private VoteType voteType;
    
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId")//, referencedColumnName = "postId")
    private Post post;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")//, referencedColumnName = "userId")
    private User user;
}

enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1),
    ;

    VoteType(int direction) {
    }
}
