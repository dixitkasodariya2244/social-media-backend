package org.example.repository;

import org.example.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    // Find all pending requests for a specific user
    List<FriendRequest> findByReceiverIdAndStatus(Long receiverId, String status);

    // Find all requests sent by a specific user
    List<FriendRequest> findBySenderIdAndStatus(Long senderId, String status);
}