package org.example.controller;

import org.example.DTO.UserDTO;
import org.example.entity.StatusUpdate;
import org.example.entity.User;
import org.example.services.StatusUpdateService;
import org.example.services.UserServices;
import org.example.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class StatusController {
    @Autowired
    private UserServices userService;

    @Autowired
    private StatusUpdateService commentUpdateService;

    // Post a status update
    @PostMapping("/{userId}/status")
    public ResponseEntity<StatusUpdate> postStatus(@PathVariable Long userId, @RequestBody String text) {
        StatusUpdate statusUpdate = commentUpdateService.postStatus(userId, text);
        if (statusUpdate != null) {
            return ResponseEntity.ok(statusUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/status")
    public ResponseEntity<UserDTO> getUserWithStatusUpdates(@PathVariable Long userId) {
        Optional<User> user = userService.getUserByIdWithStatusUpdates(userId);
        return user.map(UserMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
