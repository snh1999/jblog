package com.example.jblog.repository;

import com.example.jblog.model.Group;
import com.example.jblog.model.GroupMember;
import com.example.jblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepo extends JpaRepository<GroupMember, String> {
    @Query("SELECT gm.user FROM GroupMember gm WHERE gm.group = :group")
    List<User> findUsersByGroup(@Param("group") Group group);
    List<GroupMember> findByGroup(Group group);

    @Query("SELECT gm.group FROM GroupMember gm WHERE gm.user = :user")
    List<User> findGroupsByUser(@Param("user") User user);
    List<GroupMember> findByUser(User user);

    Optional<GroupMember> findByGroupAndUser(Group group, User user);
}
