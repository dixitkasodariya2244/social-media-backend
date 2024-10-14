package org.example.services;

import org.example.DTO.StatusUpdateDTO;
import org.example.DTO.UploadDTO;
import org.example.entity.StatusUpdate;
import org.example.entity.Upload;
import org.example.entity.User;
import org.example.repository.StatusUpdateRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class StatusUpdateService {
    @Autowired
    private StatusUpdateRepository statusUpdateRepository;

    @Autowired
    private UploadingService uploadingService;
    @Autowired
    private UserRepository userRepository;


    public StatusUpdate postStatusWithUploads(Long userId, String text, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            StatusUpdate statusUpdate = new StatusUpdate();
            statusUpdate.setText(text);
            statusUpdate.setUser(user);
            statusUpdate.setTimestamp(LocalDateTime.now());

            // Handle file uploads
            if (file != null && !file.isEmpty()) {
                Upload uploads = uploadingService.uploadFile(userId, file);
                statusUpdate.setUploads(uploads); // Assuming the relationship is set correctly
            }

            return statusUpdateRepository.save(statusUpdate);
        }
        return null;
    }

    public Optional<StatusUpdateDTO> getStatusUpdateWithUpload(Long statusUpdateId) {
        Optional<StatusUpdate> statusUpdateOptional = statusUpdateRepository.findById(statusUpdateId);

        if (statusUpdateOptional.isPresent()) {
            StatusUpdate statusUpdate = statusUpdateOptional.get();
            Upload upload = statusUpdate.getUploads();

            // Map the StatusUpdate and Upload into the DTO
            StatusUpdateDTO dto = new StatusUpdateDTO();
            dto.setId(statusUpdate.getId());
            dto.setText(statusUpdate.getText());
            dto.setTimestamp(statusUpdate.getTimestamp());

            if (upload != null) {
                UploadDTO uploadDTO = new UploadDTO();
                uploadDTO.setId(upload.getId());
                uploadDTO.setFileName(upload.getFileName());
                uploadDTO.setFileUrl(upload.getFileUrl());
                uploadDTO.setFileType(upload.getFileType());

                dto.setUploads(uploadDTO); // set the upload DTO
            }

            return Optional.of(dto);
        }
        return Optional.empty();
    }
    // Delete a status update
    public boolean deleteStatusUpdate(Long userId, Long postId) {
        Optional<StatusUpdate> statusUpdateOptional = statusUpdateRepository.findById(postId);
        if (statusUpdateOptional.isPresent()) {
            StatusUpdate statusUpdate = statusUpdateOptional.get();
            if (statusUpdate.getUser().getId().equals(userId)) {
                // Delete associated uploads
                if (statusUpdate.getUploads() != null) {
                    uploadingService.deleteFile(statusUpdate.getUploads().getId());
                }

                // Delete the status update
                statusUpdateRepository.delete(statusUpdate);
                return true;
            }
        }
        return false;
    }

    public Optional<StatusUpdate> getCommentUpdateById(Long statusUpdateId) {
        // Use the instance to call findById
        return statusUpdateRepository.findById(statusUpdateId);
    }

    // Get status updates for a user
    public List<StatusUpdate> getStatusUpdatesByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> new ArrayList<>(user.getStatusUpdates())).orElse(null);
    }
    // Fetch all relevant status updates
    public List<StatusUpdate> getAllRelevantStatusUpdates(Long userId) {
        return userRepository.findAllRelevantStatusUpdates(userId);
    }


}
