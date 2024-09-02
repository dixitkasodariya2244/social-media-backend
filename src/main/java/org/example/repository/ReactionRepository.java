package org.example.repository;

import org.example.entity.Reaction;
import org.example.entity.ReactionType;
import org.example.entity.StatusUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    int countByStatusUpdateAndType(StatusUpdate statusUpdate, ReactionType type);
}
