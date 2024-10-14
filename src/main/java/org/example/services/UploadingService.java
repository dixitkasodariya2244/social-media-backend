package org.example.services;

import org.example.entity.Upload;
import org.example.entity.User;
import org.example.repository.UploadRepository;
import org.example.repository.UserRepository;
import org.example.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

//
//            String fileName = file.getOriginalFilename();
//            String fileType = file.getContentType();

            // Generate a unique filename
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String fileType = file.getContentType();
            Upload uploadData = uploadRepository.save(Upload.builder()
                    .fileName(uniqueFileName)
                    .fileType(fileType)
                    .fileUrl("/"+ userId + "/download/" + uniqueFileName)
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

    public byte[] downloadImage(Long userId, String fileName) {
        Optional<Upload> uploadOptional = uploadRepository.findByUserIdAndFileName(userId, fileName); // Update query
        if (uploadOptional.isPresent()) {
            byte[] images = UploadUtils.decompressImage(uploadOptional.get().getImageData());
            return images;
        } else {
            throw new RuntimeException("Image not found for this user");
        }
    }
//    // Method to delete a file
    public void deleteFile(Long uploadId) {
        Optional<Upload> uploadOptional = uploadRepository.findById(uploadId);
        if (uploadOptional.isPresent()) {

            uploadRepository.deleteById(uploadId);
        }
    }

}
