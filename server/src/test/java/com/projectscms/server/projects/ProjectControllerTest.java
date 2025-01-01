package com.projectscms.server.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectscms.server.tasks.Task;
import com.projectscms.server.tasks.TaskService;
import com.projectscms.server.users.Role;
import com.projectscms.server.users.User;
import com.projectscms.server.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper;

    private Task task;
    private User user;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("TestName");
        user.setLastName("TestLastName");
        user.setRoles(Set.of(Role.USER));
        user.setId(1L);
        userService.createUser(user);
        Project project = new Project();
        project.setProjectName("Project Name");
        project.setProjectDescription("Project description");
        project.setProjectOwner(user);
        project.setProjectMaintainers(Set.of(user));
        projectService.addProject(project);
        task = new Task();
        task.setTaskName("Task Name");
        task.setDescription("Task description");
        task.setProject(project);
        taskService.addTask(task);

    }

    @Test
    void shouldNotCreateProjectIfNotAdmin() throws Exception {
        Project project = new Project();
        project.setProjectName("Project Name");
        project.setProjectDescription("Project description");
        project.setProjectOwner(user);
        project.setProjectMaintainers(Set.of(user));
        Long ownerId = user.getId();
        mockMvc.perform(post("/projects")
                        .param("ownerId", ownerId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllProjectsWithAdminRole() throws Exception {

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetProjectByIdWithAdminRole() throws Exception {

        mockMvc.perform(get("/projects/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateProjectIfAdmin() throws Exception {

        Project updatedProject = new Project();
        updatedProject.setProjectName("Test Project 2");
        updatedProject.setProjectDescription("Test Description 2");
        updatedProject.setProjectOwner(user);
        updatedProject.setProjectMaintainers(Set.of(user));
        mockMvc.perform(patch("/projects/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProject)))
                .andExpect(status().isOk());
    }


}
