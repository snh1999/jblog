package com.example.jblog.model;

import com.example.jblog.model.enums.PostCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @NotBlank(message = "Title cannot be empty or Null")
    private String title;
    @Column(unique = true)
    private String url;
    @Lob
    private String description;
    private Integer voteCount;
    @Builder.Default
    private Instant createdAt = Instant.now();
    private PostCategory category; // create index on this

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "authorId") // , referencedColumnName = "userId")
    private User author;

    @ManyToOne
    @JoinColumn(name = "groupId") // , referencedColumnName = "groupId")
    private Group group;

    @JsonIgnore
    @OneToMany(mappedBy = "parentPost", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;
}

