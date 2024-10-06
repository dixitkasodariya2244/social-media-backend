package org.example.services;

import org.example.DTO.FriendRequestDTO;
import org.example.DTO.UserDTO;
import org.example.entity.FriendRequest;
import org.example.entity.User;
import org.example.repository.FriendRequestRepository;
import org.example.repository.UserRepository;
import org.example.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendRequestsService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean sendFriendRequest(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId).orElse(null);
        User receiver = userRepository.findById(receiverId).orElse(null);

        if (sender != null && receiver != null) {
            FriendRequest request = new FriendRequest(sender, receiver, "pending");
            friendRequestRepository.save(request);
            return true;
        }
        return false;
    }

    public List<FriendRequestDTO> getPendingRequests(Long userId) {
        List<FriendRequest> requests = friendRequestRepository.findByReceiverIdAndStatus(userId, "pending");

        // Map to DTO
        return requests.stream()
                .map(request -> new FriendRequestDTO(
                        request.getId(),
                        request.getSender().getId(),
                        request.getSender().getName(),
                        request.getReceiver().getId(),
                        request.getStatus()))
                .collect(Collectors.toList());
    }

    //    public boolean acceptFriendRequest(Long requestId) {
//        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
//        if (request != null) {
//            request.setStatus("accepted");
//            friendRequestRepository.save(request);
//            return true;
//        }
//        return false;
//    }
    public boolean acceptFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus("accepted");
            friendRequestRepository.save(request);

            // Add sender and receiver to each other's friends list
            User sender = request.getSender();
            User receiver = request.getReceiver();

            sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);

            userRepository.save(sender);
            userRepository.save(receiver);

            return true;
        }
        return false;
    }

    public boolean rejectFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus("rejected");
            friendRequestRepository.save(request);
            return true;
        }
        return false;
    }

    //    public List<UserDTO> getFriends(Long userId) {
//        User user = userRepository.findById(userId).orElse(null);
//
//        if (user != null) {
//            List<UserDTO> friendsDTOs = user.getFriends().stream()
//                    .map(UserMapper::toDTO)
//                    .collect(Collectors.toList());
//
//            // Log the number of friends
//            System.out.println("Number of friends for user " + userId + ": " + friendsDTOs.size());
//            return friendsDTOs;
//        }
//        return Collections.emptyList(); // or handle the case when the user is not found
//    }
    public List<UserDTO> getFriends(Long userId) {
        User user = userRepository.findByIdWithFriends(userId).orElse(null);

        if (user != null) {
            List<UserDTO> friendsDTOs = user.getFriends().stream()
                    .map(UserMapper::toDTO)
                    .collect(Collectors.toList());

            return friendsDTOs;
        }
        return Collections.emptyList();
    }

    public FriendRequestDTO getFriendRequestById(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            return new FriendRequestDTO(
                    request.getId(),
                    request.getSender().getId(),
                    request.getSender().getName(),
                    request.getReceiver().getId(),  // Add this line to get the receiver's ID
                    request.getStatus()
            );
        }
        return null; // Handle case where request is not found
    }
}
