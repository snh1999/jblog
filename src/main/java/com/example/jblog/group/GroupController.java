package com.example.jblog.group;

import com.example.jblog.group.dto.AddUserDto;
import com.example.jblog.group.dto.GroupDto;
import com.example.jblog.model.Group;
import com.example.jblog.model.GroupMember;
import com.example.jblog.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@AllArgsConstructor
@Slf4j
public class GroupController {
    private GroupService groupService;

    @PostMapping("")
    public ResponseEntity<Group> create(@RequestBody GroupDto groupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.create(groupDto));
    }

    @GetMapping("")
    public ResponseEntity<List<Group>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Group> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(groupService.findById(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody GroupDto updateDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(groupService.update(id, updateDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(groupService.delete(id));
    }

    @PostMapping("{id}")
    public ResponseEntity<GroupMember> joinGroup(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.joinGroup(id));
    }

    @PostMapping("{id}/members")
    public ResponseEntity<GroupMember> addMembers(@PathVariable String id, @RequestBody AddUserDto addUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.addMember(id, addUserDto.getUserName()));
    }

    @GetMapping("{id}/members")
    public ResponseEntity<List<GroupMember>> getMembers(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getAllMembers(id));
    }

    @DeleteMapping("{id}/members")
    public ResponseEntity<String> removeMembers(@PathVariable String id, @RequestBody AddUserDto addUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.removeMember(id, addUserDto.getUserName()));
    }

    @PostMapping("{id}/moderators")
    public ResponseEntity<String> addModerators(@PathVariable String id,  @RequestBody AddUserDto addUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.addModerator(id, addUserDto.getUserName()));
    }

    @GetMapping("{id}/moderators")
    public ResponseEntity<List<User>> getModerators(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getAllModerators(id));
    }



//    @PatchMapping("/{id}")
//    public User updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
//        if (updatedUser.getName() != null) {
//            user.setName(updatedUser.getName());
//        }
//        return userRepository.save(user);
//    }


    // get all posts

}
