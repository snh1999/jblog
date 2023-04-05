package com.example.jblog.post.dto;

import com.example.jblog.model.Comment;
import com.example.jblog.model.Group;
import com.example.jblog.model.User;
import com.example.jblog.model.enums.PostCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String title;
    private String url;
    private String description;
    private PostCategory category; // create index on this
    private String groupId;
}