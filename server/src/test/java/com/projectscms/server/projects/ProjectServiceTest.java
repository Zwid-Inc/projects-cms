package com.projectscms.server.projects;

import com.projectscms.server.tasks.Task;
import com.projectscms.server.users.User;
import com.projectscms.server.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProjectServiceTest {

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ProjectServiceImpl projectService;

    private User sampleUser;
    private Project sampleProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User();
        sampleUser.setId(10L);
        sampleUser.setEmail("testuser@example.com");
        sampleUser.setName("Jan");
        sampleUser.setLastName("Kowalski");
        userRepository.save(sampleUser);
        sampleProject = new Project();
        sampleProject.setId(1L);
        sampleProject.setProjectName("Test project");
        sampleProject.setProjectOwner(sampleUser);
        sampleProject.setProjectMaintainers(Set.of(sampleUser));
        sampleProject.setProjectDescription("Test project description");
        sampleProject.setTaskList(new ArrayList<>(List.of(new Task())));
    }

    @Test
    void testCreateProject() {
        // Given
        when(projectRepository.saveAndFlush(sampleProject)).thenReturn(sampleProject);

        // When
        projectService.addProject(sampleProject);

        // Then
        verify(projectRepository, times(1)).saveAndFlush(sampleProject);
    }

    @Test
    void testGetAllProjects() {
        // Given
        when(projectRepository.findAll()).thenReturn(List.of(sampleProject));

        // When
        List<Project> projects = projectService.getAllProjects();

        // Then
        assertNotNull(projects);
        assertEquals(1, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProjectById() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

        // When
        Optional<Project> foundProject = projectService.getProjectById(1L);

        // Then
        assertTrue(foundProject.isPresent());
        assertEquals("Test project", foundProject.get().getProjectName());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProjectByProjectName() {
        // Given
        String name = "Test project";
        when(projectRepository.findByProjectName(name)).thenReturn(Optional.of(sampleProject));

        // When
        Optional<Project> foundProject = projectService.getProjectByProjectName(name);

        // Then
        assertTrue(foundProject.isPresent());
        assertEquals(name, foundProject.get().getProjectName());
        verify(projectRepository, times(1)).findByProjectName(name);
    }

    @Test
    void testUpdateProject_ProjectExists() {
        // Given
        Project projectToUpdate = new Project();
        projectToUpdate.setProjectName("New test project");
        projectToUpdate.setProjectDescription("New project description");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));
        when(projectRepository.saveAndFlush(any(Project.class))).thenReturn(sampleProject);

        // When
        projectService.updateProjectById(1L, projectToUpdate);

        // Then
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).saveAndFlush(any(Project.class));
    }

    @Test
    void testUpdateProject_ProjectNotExists() {
        // Given
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Project updatedResult = projectService.updateProjectById(999L, sampleProject);

        // Then
        assertNull(updatedResult);
        verify(projectRepository, times(1)).findById(999L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void testDeleteProjectById_ProjectExists() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

        // When
        projectService.deleteProjectById(1L);

        // Then
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProjectById_ProjectNotExists() {
        // Given
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        boolean result = projectService.deleteProjectById(999L);

        // Then
        assertFalse(result);
        verify(projectRepository, times(1)).findById(999L);
        verify(projectRepository, never()).save(any(Project.class));
        verify(projectRepository, never()).deleteById(999L);
    }
}
