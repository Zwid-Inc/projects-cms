package com.projectscms.server.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByProjectOwnerEmail(String email);
    Optional<Project> findByProjectName(String projectName);

    @Query("SELECT p FROM Project p JOIN p.projectMaintainers pm WHERE pm.userId = :userId")
    List<Project> findByMaintainersContains(@Param("userId") Long userId);

}