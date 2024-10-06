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

        // Extract the file extension (e.g., jpg, png) to determine the media type
        String fileExtension = getFileExtension(fileName).toLowerCase();
        MediaType mediaType = getMediaTypeForFileExtension(fileExtension);

        // If the file extension is not recognized, return an error response (optional)
        if (mediaType == null) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Unsupported file type: " + fileExtension);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(getMediaTypeForFileExtension(getFileExtension(fileName)))
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(imageData);
    }

    // Utility method to extract file extension
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    // Utility method to map file extensions to media types
    private MediaType getMediaTypeForFileExtension(String extension) {
        switch (extension) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return null; // Unsupported file type
        }
    }

//    @PostMapping("/{userId}/delete/{fileName}")
//    public ResponseEntity<?> deleteImage(@PathVariable Long userId, @PathVariable String fileName) {
//        boolean isDeleted = uploadingService.deleteImage(userId, fileName);
//        if (isDeleted) {
//            return ResponseEntity.ok("Image deleted successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found.");
//        }
//    }
}
