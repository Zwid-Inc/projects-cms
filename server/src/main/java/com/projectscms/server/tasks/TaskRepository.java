package com.projectscms.server.tasks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTaskNameContainingIgnoreCase(String taskName);
    Page<Task> findByProjectId(Long projectId, Pageable pageable);

}