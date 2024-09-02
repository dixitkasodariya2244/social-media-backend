package org.example.services;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {
    @Autowired
    private UserRepository userRepository;

    // Follow a user
    public void followUser(Long userId, Long followUserId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> followUserOptional = userRepository.findById(followUserId);

        if (userOptional.isPresent() && followUserOptional.isPresent()) {
            User user = userOptional.get();
            User followUser = followUserOptional.get();
            user.getFollowing().add(followUser);
            followUser.getFollowers().add(user);
            userRepository.save(user);
            userRepository.save(followUser);
        }
    }

    // Unfollow a user
    public void unfollowUser(Long userId, Long unfollowUserId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> unfollowUserOptional = userRepository.findById(unfollowUserId);

        if (userOptional.isPresent() && unfollowUserOptional.isPresent()) {
            User user = userOptional.get();
            User unfollowUser = unfollowUserOptional.get();
            user.getFollowing().remove(unfollowUser);
            unfollowUser.getFollowers().remove(user);
            userRepository.save(user);
            userRepository.save(unfollowUser);
        }
    }
}
