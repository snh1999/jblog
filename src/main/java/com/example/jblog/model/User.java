package com.example.jblog.model;

import com.example.jblog.model.enums.ROLE;
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
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required and has to be unique")
    @Column(unique = true)
    private String email;
    @Builder.Default
    private Instant joinedAt = Instant.now();
    @Builder.Default
    private boolean enabled = false;

    @Builder.Default
    private ROLE role = ROLE.USER;
    @Builder.Default
    private Instant passwordChangedAt = Instant.now();

    @ManyToMany(mappedBy = "members", fetch = LAZY)
    private List<Group> groups;

    @OneToMany(mappedBy = "owner", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Group> ownedGroups;

    @OneToMany(mappedBy = "author", fetch = LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}

