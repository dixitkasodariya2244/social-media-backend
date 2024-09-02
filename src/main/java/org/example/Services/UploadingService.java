package org.example.Services;

import org.example.Entity.Upload;
import org.example.Entity.User;
import org.example.Repository.UploadRepository;
import org.example.Repository.UserRepository;
import org.example.Utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class UploadingService {
    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to handle file upload
    public Upload uploadFile(Long userId, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String fileName = file.getOriginalFilename();
            String fileType = file.getContentType();
            Upload uploadData = uploadRepository.save(Upload.builder()
                    .fileName(fileName)
                    .fileType(fileType)
                    .uploadedAt(new Date())
                    .user(user)
                    .imageData(UploadUtils.compressImage(file.getBytes())).build());
            return uploadRepository.save(uploadData);
        }
        return null;
    }

    // Method to retrieve user uploads
    public List<Upload> getUploadsByUserId(Long userId) {
        return uploadRepository.findByUserId(userId);
    }

    //downloading images with user id
    public byte[] downloadImage(Long userId, String fileName) {
        Optional<Upload> uploadOptional = uploadRepository.findById(userId);
        if (uploadOptional.isPresent()) {
            byte[] images = UploadUtils.decompressImage(uploadOptional.get().getImageData());
            return images;
        }else {
            // Handle the case where the image is not found or does not belong to the user
            throw new RuntimeException("Image not found for this user");
        }
    }
}
