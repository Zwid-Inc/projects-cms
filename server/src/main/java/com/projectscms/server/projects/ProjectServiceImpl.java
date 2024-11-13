package com.projectscms.server.projects;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private final ProjectRepository projectRepository;

    @Transactional
    @Override
    public boolean addProject(Project project) {
        LOG.debug(">>>addProject " + project.toString() + "<<<");
        projectRepository.saveAndFlush(project);
        return projectRepository.existsById(project.getId());
    }

    @Override
    public List<Project> getAllProjects() {
        LOG.debug(">>>getAllProjects<<<");
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getAllUserProjects(String email) {
        LOG.debug(">>>getAllUserProjects; Username: " + email);
        return projectRepository.findByProjectOwnerEmail(email);
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        LOG.debug(">>>getProjectById<<<");
        return projectRepository.findById(id);
    }

    @Override
    public Optional<Project> getProjectByProjectName(String projectName) {
        LOG.debug(">>>getProjectByProjectName<<<");
        return projectRepository.findByProjectName(projectName);
    }



    @Transactional
    @Override
    public Project updateProjectById(Long id, Project project) {
        return projectRepository.findById(id).map(dbProject -> {
            dbProject.setProjectName(project.getProjectName());
            dbProject.setProjectDescription(project.getProjectDescription());
            //TODO AFTER ADDING API FOR TASKS
            // dbProject.setTaskList(project.getTaskList());
            dbProject.setProjectMaintainers(project.getProjectMaintainers());
            dbProject.setReleaseDate(project.getReleaseDate());
            return projectRepository.saveAndFlush(dbProject);
        }).orElse(null);
    }

    @Transactional
    @Override
    public boolean deleteProjectById(Long id) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();

            project.setProjectOwner(null);

            project.getProjectMaintainers().clear();
            project.getTaskList().clear();

            projectRepository.save(project);

            projectRepository.deleteById(id);
            LOG.debug("Project with ID: " + id + " deleted successfully.");
            return true;
        } else {
            LOG.warn("Project with ID: " + id + " not found.");
        }
        return false;
    }



}