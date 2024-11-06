package com.projectscms.server.projects;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    boolean addProject(Project project);
    List<Project> getAllProjects();
    List<Project> getAllUserProjects(String username);
    Optional<Project> getProjectById(Long id);
    Optional<Project> getProjectByProjectName(String projectName);
    Project updateProjectById(Long id, Project project);
    boolean deleteProjectById(Long id);

}