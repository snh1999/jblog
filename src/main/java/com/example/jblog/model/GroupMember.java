package com.example.jblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", referencedColumnName = "groupId")
    private Group group;
    private boolean isUserJoined;
    private boolean isUserApproved;

    @Builder.Default
    private Instant joinedAt = Instant.now();

    public String getUserId() {
        return user.getId();
    }

    public String groupId() {
        return group.getId();
    }
}