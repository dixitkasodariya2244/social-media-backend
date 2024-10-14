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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class StatusController {
    @Autowired
    private UserServices userService;

    @Autowired
    private StatusUpdateService statusUpdateService;

    @PostMapping("/{userId}/post")
    public ResponseEntity<StatusUpdate> postStatusWithUploads(
            @PathVariable Long userId,
            @RequestParam("text") String text,
            @RequestParam(value = "files", required = false) MultipartFile files) {

        try {
            StatusUpdate statusUpdate = statusUpdateService.postStatusWithUploads(userId, text, files);
            if (statusUpdate != null) {
                return ResponseEntity.ok(statusUpdate);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

@GetMapping("/{userId}/post")
public ResponseEntity<UserDTO> getUserWithStatusUpdates(@PathVariable Long userId) {
    Optional<User> user = userService.getUserByIdWithStatusUpdates(userId);

    if (user.isPresent()) {
        UserDTO userDTO = UserMapper.toDTO(user.get());

        // For each status update, also include image URLs or data
        userDTO.getStatusUpdates().forEach(statusUpdateDTO -> {
            if (statusUpdateDTO.getUploads() != null) {
                // Add the image URL or Base64 data to the status update DTO
                String imageUrl = "/"+ userId +"/download/" + statusUpdateDTO.getUploads().getFileName();
                statusUpdateDTO.setImageUrl(imageUrl);  // Assuming there's a field for the image URL
            }
        });

        return ResponseEntity.ok(userDTO);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    // Delete a status update
    @DeleteMapping("/{userId}/post/{postId}")
    public ResponseEntity<Void> deleteStatusUpdate(@PathVariable Long userId, @PathVariable Long postId) {
        boolean isDeleted = statusUpdateService.deleteStatusUpdate(userId, postId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
