package org.example.controller;

import org.example.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FollowController {
    @Autowired
    private FollowService followService;
    @PostMapping("/{userId}/follow/{followUserId}")
    public ResponseEntity<Void> followUser(@PathVariable Long userId, @PathVariable Long followUserId) {
        followService.followUser(userId, followUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/unfollow/{unfollowUserId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long userId, @PathVariable Long unfollowUserId) {
        followService.unfollowUser(userId, unfollowUserId);
        return ResponseEntity.ok().build();
    }
}
