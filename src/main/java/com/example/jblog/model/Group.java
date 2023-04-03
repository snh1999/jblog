package com.example.jblog.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_group")
public class Group {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @NotBlank(message = "Group name is required")
    private String name;
    @NotBlank(message = "Group must have a description")
    private String description;
    @Builder.Default
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "group", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany(fetch = LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "member_table", 
        joinColumns = { @JoinColumn(name = "group_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "member_id") },
        uniqueConstraints = {@UniqueConstraint(
            columnNames = {"group_id", "member_id"}
        )}
    )
    private List<User> members;

    @ManyToMany(fetch = LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "moderator_table", 
        joinColumns = { @JoinColumn(name = "group_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "moderator_id") },
        uniqueConstraints = {@UniqueConstraint(
            columnNames = {"group_id", "moderator_id"})}
    )
    private List<User> moderators;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;
}