package org.example.repository;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Method to find a User by email
    Optional<User> findByEmail(String email);

    // Method to find a User by id (already provided by JpaRepository)
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.statusUpdates WHERE u.id = :id")
    Optional<User> findByIdWithFriendsAndStatusUpdates(Long id);

}
