package org.example.services;

import org.example.entity.*;
import org.example.repository.ReactionRepository;
import org.example.repository.StatusUpdateRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private StatusUpdateRepository statusUpdateRepository;

    @Autowired
    private UserRepository userRepository;

    public Reaction addReaction(Long userId, Long statusUpdateId, ReactionType type) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<StatusUpdate> statusUpdateOptional = statusUpdateRepository.findById(statusUpdateId);

        if (userOptional.isPresent() && statusUpdateOptional.isPresent()) {
            User user = userOptional.get();
            StatusUpdate statusUpdate = statusUpdateOptional.get();

            Reaction reaction = Reaction.builder()
                    .user(user)
                    .statusUpdate(statusUpdate)
                    .type(type)
                    .build();

            return reactionRepository.save(reaction);
        }
        return null;
    }

    public int countReactions(Long statusUpdateId, ReactionType type) {
        Optional<StatusUpdate> statusUpdateOptional = statusUpdateRepository.findById(statusUpdateId);

        if (statusUpdateOptional.isPresent()) {
            StatusUpdate statusUpdate = statusUpdateOptional.get();
            return reactionRepository.countByStatusUpdateAndType(statusUpdate, type);
        }
        return 0;
    }
    public Reaction getUserReaction(Long userId, Long statusUpdateId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<StatusUpdate> statusUpdateOptional = statusUpdateRepository.findById(statusUpdateId);

        if (userOptional.isPresent() && statusUpdateOptional.isPresent()) {
            User user = userOptional.get();
            StatusUpdate statusUpdate = statusUpdateOptional.get();

            // Fetch the reaction with a limit of 1
            return reactionRepository.findByUserAndStatusUpdate(user, statusUpdate)
                    .orElse(null);
        }
        return null;
    }
}
