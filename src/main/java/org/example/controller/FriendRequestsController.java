package org.example.controller;

import org.example.DTO.FriendRequestDTO;
import org.example.DTO.UserDTO;
import org.example.entity.FriendRequest;
import org.example.services.FriendRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FriendRequestsController {
    @Autowired
    private FriendRequestsService friendRequestsService;

    @PostMapping("/{senderId}/friend-request/{receiverId}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
        boolean success = friendRequestsService.sendFriendRequest(senderId, receiverId);
        if (success) {
            return ResponseEntity.ok("Friend request sent.");
        } else {
            return ResponseEntity.badRequest().body("Friend request failed.");
        }
    }

    @GetMapping("/{userId}/friend-requests")
    public ResponseEntity<List<FriendRequestDTO>> getPendingRequests(@PathVariable Long userId) {
        List<FriendRequestDTO> requests = friendRequestsService.getPendingRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/friend-requests/{requestId}/accept")
    public ResponseEntity<List<UserDTO>> acceptRequest(@PathVariable Long requestId) {
        // Get the friend request details from the service
        FriendRequestDTO friendRequest = friendRequestsService.getFriendRequestById(requestId);
        if (friendRequest != null) {
            boolean success = friendRequestsService.acceptFriendRequest(requestId);
            if (success) {
                // After accepting the request, fetch the list of friends for the receiver
                List<UserDTO> friends = friendRequestsService.getFriends(friendRequest.getReceiverId());
                return ResponseEntity.ok(friends);
            }
        }
        return ResponseEntity.badRequest().body(null); // Handle case where request acceptance fails
    }

    @PostMapping("/friend-requests/{requestId}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long requestId) {
        boolean success = friendRequestsService.rejectFriendRequest(requestId);
        if (success) {
            return ResponseEntity.ok("Friend request rejected.");
        } else {
            return ResponseEntity.badRequest().body("Failed to reject friend request.");
        }
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<UserDTO>> getFriends(@PathVariable Long userId) {
        List<UserDTO> friends = friendRequestsService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }
}
