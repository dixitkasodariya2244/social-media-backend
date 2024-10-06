package org.example.controller;

import org.example.DTO.UserDTO;
import org.example.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    // Get friends list of a user
//    @GetMapping("/{userId}/friends")
//    public Set<UserDTO> getFriendsList(@PathVariable Long userId) {
//        return friendService.getFriendsList(userId);
//    }

    // Remove a friend from the user's friends list
    @DeleteMapping("/{userId}/remove/{friendId}")
    public void removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.removeFriend(userId, friendId);
    }
}
