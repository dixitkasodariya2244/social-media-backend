package org.example.Repository;

import org.example.Entity.StatusUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusUpdateRepository  extends JpaRepository<StatusUpdate, Long> {
}
