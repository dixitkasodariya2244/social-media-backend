package org.example.services;

import org.example.DTO.UserDTO;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendService {
    @Autowired
    private UserRepository userRepository;

    // Get friends list
    public Set<UserDTO> getFriendsList(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getFriends().stream()
                    .map(UserMapper::toDTO)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    // Remove a friend
    public void removeFriend(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();

            user.getFriends().remove(friend);
            friend.getFriends().remove(user);

            userRepository.save(user);
            userRepository.save(friend);
        }
    }
}
