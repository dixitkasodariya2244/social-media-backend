package org.example.Controller;

import org.example.Services.FriendRequestsService;
import org.example.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/{userId}/accept-friend-request/{senderId}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long userId, @PathVariable Long senderId) {
        boolean success = friendRequestsService.acceptFriendRequest(userId, senderId);
        if (success) {
            return ResponseEntity.ok("Friend request accepted.");
        } else {
            return ResponseEntity.badRequest().body("Failed to accept friend request.");
        }
    }

    @PostMapping("/{userId}/reject-friend-request/{senderId}")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable Long userId, @PathVariable Long senderId) {
        boolean success = friendRequestsService.rejectFriendRequest(userId, senderId);
        if (success) {
            return ResponseEntity.ok("Friend request rejected.");
        } else {
            return ResponseEntity.badRequest().body("Failed to reject friend request.");
        }
    }
}
