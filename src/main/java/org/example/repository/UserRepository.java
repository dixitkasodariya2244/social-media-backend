package org.example.repository;

import org.example.entity.StatusUpdate;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Method to find a User by email
    Optional<User> findByEmail(String email);

    // Method to find a User by id (already provided by JpaRepository)
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.statusUpdates WHERE u.id = :id")
    Optional<User> findByIdWithFriendsAndStatusUpdates(Long id);

    @Query("SELECT su FROM StatusUpdate su WHERE su.user IN ("
            + "SELECT u FROM User u WHERE u.id = :users_id "
            + "OR u IN (SELECT f FROM User u JOIN u.friends f WHERE u.id = :users_id) "
            + "OR u IN (SELECT f FROM User u JOIN u.following f WHERE u.id = :users_id) "
            + "OR u IN (SELECT f FROM User u JOIN u.followers f WHERE u.id = :users_id))")
    List<StatusUpdate> findAllRelevantStatusUpdates(@Param("users_id") Long userId);

}
