package org.example.repository;

import org.example.entity.FollowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long> {

    // Find all pending requests for a specific user
    List<FollowRequest> findByFollowingIdAndStatus(Long followingId, String status);

    // Find all requests sent by a specific user
    List<FollowRequest> findByFollowerIdAndStatus(Long followerId, String status);
}
