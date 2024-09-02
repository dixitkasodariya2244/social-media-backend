package org.example.repository;

import org.example.entity.StatusUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusUpdateRepository  extends JpaRepository<StatusUpdate, Long> {
}
