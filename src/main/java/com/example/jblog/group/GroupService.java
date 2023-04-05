package com.example.jblog.group;

import com.example.jblog.auth.AuthService;
import com.example.jblog.filter.GenericException;
import com.example.jblog.filter.ResourceNotFoundException;
import com.example.jblog.group.dto.GroupDto;
import com.example.jblog.model.Group;
import com.example.jblog.model.GroupMember;
import com.example.jblog.model.User;
import com.example.jblog.model.enums.GroupType;
import com.example.jblog.repository.GroupMemberRepo;
import com.example.jblog.repository.GroupRepo;
import com.example.jblog.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GroupService {
    private GroupRepo groupRepo;
    private GroupMemberRepo groupMemberRepo;
    private UserRepo userRepo;
    private AuthService authService;

    public Group create(GroupDto groupDto) {
        User user = authService.getCurrentUser();
        Group group = Group.builder()
                .name(groupDto.getName())
                .owner(user)
                .description(groupDto.getDescription())
                .url(groupDto.getUrl())
                .build();

        return groupRepo.save(group);
    }

    public List<Group> findAll() {
        return groupRepo.findAll();
    }

    public Group findById(String id) {
        return groupRepo.findByIdOrUrl(id, id).orElseThrow(() -> new ResourceAccessException("Not found"));
    }

    public String update(String id, GroupDto groupDto)  {
        Group group = this.findById(id);
        this.isUserOwner(group.getOwner());

        if (!groupDto.getName().isBlank()) group.setName(groupDto.getName());
        if (!groupDto.getDescription().isBlank()) group.setDescription(groupDto.getDescription());
        if (!groupDto.getUrl().isBlank()) group.setName(groupDto.getUrl());

        groupRepo.save(group);
        return "Updated Successfully";
    }

    public String delete(String id)  {
        Group group = this.findById(id);
        this.isUserOwner(group.getOwner());

        groupRepo.delete(group);
        return "Deleted Successfully";

    }

    private void isUserOwner(User owner) {
        String currentUser = authService.getCurrentUser().getId();
        if(!currentUser.equals(owner.getId())) throw new GenericException("Unauthorized, Only Group Owner can perform this operation");
    }


    // get all members
    public List<GroupMember> getAllMembers(String id) {
        Group group = this.findById(id);
        return group.getMembers();
    }
    // get all moderators
    public List<User> getAllModerators(String id) {
        Group group = this.findById(id);
        return group.getModerators();
    }
    // add mods - by owner
    public String addModerator(String id, String userName) {
        Group group = this.findById(id);
        isUserOwner(group.getOwner());
        User user = this.userRepo.findByUsername(userName).orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        group.getModerators().add(user);
        groupRepo.save(group);
        return "Successfully Added";
    }
    // remove mods - by owner
    public String removeModerator(String id, String userName) {
        Group group = this.findById(id);
        isUserOwner(group.getOwner());
        User user = this.userRepo.findByUsername(userName).orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        group.getModerators().remove(user);
        groupRepo.save(group);
        return "Successfully Removed";
    }
    // join group - update member list
    public GroupMember joinGroup(String id) {
        User currentUser = this.authService.getCurrentUser();
        Group group = this.findById(id);
        return addMember(group, currentUser, true);
    }
    public GroupMember addMember(String id, String userName) {
        Group group = this.findById(id);
        if (!isUserModerator(group, this.authService.getCurrentUser())) throw new GenericException("Unauthorized");
        User user = this.userRepo.findByUsername(userName).orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        return this.addMember(group, user, false);
    }
    private GroupMember addMember(Group group, User user, boolean isUserRequest) {
        boolean userApproval = group.getGroupType() == GroupType.PUBLIC;
        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .user(user)
                .isUserJoined(isUserRequest)
                .isUserApproved(userApproval)
                .build();
        return groupMemberRepo.save(groupMember);
    }
    public String leaveGroup(String id) {
        User currentUser = this.authService.getCurrentUser();
        Group group = this.findById(id);
        return removeMember(group, currentUser);
    }

    public String removeMember(String id, String userName) {
        User currentUser = authService.getCurrentUser();
        Group group = this.findById(id);
        if (isUserModerator(group, currentUser))
            throw new GenericException("Unauthorized");

        User user =  this.userRepo.findByUsername(userName).orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        return removeMember(group, user);
    }
    private String removeMember(Group group, User user) {
        GroupMember groupMember = this.groupMemberRepo.findByGroupAndUser(group, user).orElseThrow();
        this.groupMemberRepo.delete(groupMember);

        return "User no longer part of the group";
    }

    private boolean isUserModerator(Group group, User user){
        if (group.getOwner().getId().equals(user.getId())) return true;
        var moderators = group.getModerators();
        for (var moderator : moderators) {
            if(moderator.getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }


    private User getUsertoOperate(String userName, User currentUser) {
        User user;
        if (currentUser.getUsername().equals(userName)) user = currentUser;
        else user =  this.userRepo.findByUsername(userName).orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        return user;
    }


}
