package org.example.controller;

import org.example.DTO.FollowRequestDTO;
import org.example.DTO.UserDTO;
import org.example.services.FollowRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FollowRequestsController {

    @Autowired
    private FollowRequestsService followRequestsService;

    @PostMapping("/{followerId}/follow-request/{followingId}")
    public ResponseEntity<String> sendFollowRequest(@PathVariable Long followerId, @PathVariable Long followingId) {
        boolean success = followRequestsService.sendFollowRequest(followerId, followingId);
        if (success) {
            return ResponseEntity.ok("Follow request sent.");
        } else {
            return ResponseEntity.badRequest().body("Follow request failed.");
        }
    }

    @GetMapping("/{userId}/follow-requests")
    public ResponseEntity<List<FollowRequestDTO>> getPendingRequests(@PathVariable Long userId) {
        List<FollowRequestDTO> requests = followRequestsService.getPendingRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/follow-requests/{requestId}/accept")
    public ResponseEntity<String> acceptRequest(@PathVariable Long requestId) {
        boolean success = followRequestsService.acceptFollowRequest(requestId);
        if (success) {
            return ResponseEntity.ok("Follow request accepted.");
        }
        return ResponseEntity.badRequest().body("Failed to accept follow request.");
    }

    @PostMapping("/follow-requests/{requestId}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long requestId) {
        boolean success = followRequestsService.rejectFollowRequest(requestId);
        if (success) {
            return ResponseEntity.ok("Follow request rejected.");
        }
        return ResponseEntity.badRequest().body("Failed to reject follow request.");
    }
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable Long userId) {
        List<UserDTO> followers = followRequestsService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserDTO>> getFollowing(@PathVariable Long userId) {
        List<UserDTO> following = followRequestsService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }
}
