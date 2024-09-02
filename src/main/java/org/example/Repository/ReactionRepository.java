package org.example.Repository;

import org.example.Entity.Reaction;
import org.example.Entity.ReactionType;
import org.example.Entity.StatusUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    int countByStatusUpdateAndType(StatusUpdate statusUpdate, ReactionType type);
}
