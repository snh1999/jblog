package com.example.jblog.model;

import com.example.jblog.model.enums.ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.FetchType.LAZY;



import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
//    @NotBlank(message = "Name is required")
//    private String name;

    @NotBlank(message = "username is required and has to be unique")
    @Column(unique = true)
    private String username;
    @JsonIgnore
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required and has to be unique")
    @Column(unique = true)
    private String email;
    @Builder.Default
    private Instant joinedAt = Instant.now();
    @JsonIgnore
    @Builder.Default
    private boolean enabled = false;
    @JsonIgnore
    @Builder.Default
    private ROLE role = ROLE.USER;
    @JsonIgnore
    @Builder.Default
    private Instant passwordChangedAt = Instant.now();

    @JsonIgnore
//    @ManyToMany(mappedBy = "members", fetch = LAZY)
//    private List<Group> groups;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GroupMember> groups;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Group> ownedGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {return true;}

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}

