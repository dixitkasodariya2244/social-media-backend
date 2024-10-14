package org.example.repository;

import org.example.entity.Reaction;
import org.example.entity.ReactionType;
import org.example.entity.StatusUpdate;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    int countByStatusUpdateAndType(StatusUpdate statusUpdate, ReactionType type);
    Optional<Reaction> findByUserAndStatusUpdate(User user, StatusUpdate statusUpdate);

}
