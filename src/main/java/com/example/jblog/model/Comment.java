package com.example.jblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @NotEmpty
    private String text;
    @Builder.Default
    private Instant createdDate = Instant.now();

    @ManyToOne //(fetch = LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post parentPost;
    @ManyToOne //(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User author;

    // for reply to comments
    @ManyToOne
    @JoinColumn(name = "parent_comment", referencedColumnName = "id")
    private Comment parentComment;
    
    @OneToMany(fetch = LAZY, mappedBy = "parentComment")
    private List<Comment> comments;
}
