package org.example.Controller;

import org.example.DTO.UserDTO;
import org.example.Services.FriendService;
import org.example.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    // Get friends list of a user
    @GetMapping("/{userId}")
    public Set<UserDTO> getFriendsList(@PathVariable Long userId) {
        return friendService.getFriendsList(userId);
    }

    // Remove a friend from the user's friends list
    @DeleteMapping("/{userId}/remove/{friendId}")
    public void removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.removeFriend(userId, friendId);
    }
}
