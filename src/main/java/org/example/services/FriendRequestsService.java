package org.example.services;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class FriendRequestsService {
    @Autowired
    private UserRepository userRepository;

    // Send a friend request
    public boolean sendFriendRequest(Long senderId, Long receiverId) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);

        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            User sender = senderOpt.get();
            User receiver = receiverOpt.get();

            if (sender.getFriends().contains(receiver)) {
                return false; // Already friends
            }

            sender.getSentFriendRequests().add(receiver);
            receiver.getReceivedFriendRequests().add(sender);

            userRepository.save(sender);
            userRepository.save(receiver);
            return true;
        }
        return false;
    }

    // Accept a friend request
    public boolean acceptFriendRequest(Long userId, Long senderId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> senderOpt = userRepository.findById(senderId);

        if (userOpt.isPresent() && senderOpt.isPresent()) {
            User user = userOpt.get();
            User sender = senderOpt.get();

            if (user.getReceivedFriendRequests().contains(sender)) {
                user.getFriends().add(sender);
                sender.getFriends().add(user);

                user.getReceivedFriendRequests().remove(sender);
                sender.getSentFriendRequests().remove(user);

                userRepository.save(user);
                userRepository.save(sender);
                return true;
            }
        }
        return false;
    }

    // Reject a friend request
    public boolean rejectFriendRequest(Long userId, Long senderId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> senderOpt = userRepository.findById(senderId);

        if (userOpt.isPresent() && senderOpt.isPresent()) {
            User user = userOpt.get();
            User sender = senderOpt.get();

            if (user.getReceivedFriendRequests().contains(sender)) {
                user.getReceivedFriendRequests().remove(sender);
                sender.getSentFriendRequests().remove(user);

                userRepository.save(user);
                userRepository.save(sender);
                return true;
            }
        }
        return false;
    }
}
