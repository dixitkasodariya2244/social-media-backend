package org.example.repository;

import org.example.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UploadRepository extends JpaRepository<Upload, Long> {
    List<Upload> findByUserId(Long userId);
    Optional<Upload> findByUserIdAndFileName(Long userId, String fileName);

}
