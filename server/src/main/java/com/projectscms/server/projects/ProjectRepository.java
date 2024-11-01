package com.projectscms.server.projects;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByProjectOwnerEmail(String email);
    Optional<Project> findByProjectName(String projectName);

}