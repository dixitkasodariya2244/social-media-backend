package org.example.Services;

import org.example.Entity.StatusUpdate;
import org.example.Entity.User;
import org.example.Repository.StatusUpdateRepository;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class StatusUpdateService {
    @Autowired
    private StatusUpdateRepository statusUpdateRepository;

    @Autowired
    private UserRepository userRepository;


    // Post a status update
    public StatusUpdate postStatus(Long userId, String text) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            StatusUpdate statusUpdate = new StatusUpdate(text, user);
            return statusUpdateRepository.save(statusUpdate);
        }
        return null;
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

}
