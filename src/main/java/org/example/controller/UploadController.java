package org.example.controller;

import org.example.entity.Upload;
import org.example.services.UploadingService;
import org.example.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private UploadingService uploadingService;

    @PostMapping("/{userId}/upload")
    public ResponseEntity<Upload> uploadFile(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            Upload upload = uploadingService.uploadFile(userId, file);
            if (upload != null) {
                return ResponseEntity.ok(upload);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{userId}/download/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable Long userId, @PathVariable String fileName) {
        byte[] imageData = uploadingService.downloadImage(userId, fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))  // Adjust content type based on the actual image type
                .body(imageData);
    }
}
