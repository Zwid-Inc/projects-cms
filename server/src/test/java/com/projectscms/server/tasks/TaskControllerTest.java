package com.projectscms.server.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectscms.server.projects.Project;
import com.projectscms.server.projects.ProjectService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper;

    private Project project;
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
        userService.createUser(user);
        project = new Project();
        project.setProjectName("Project Name");
        project.setProjectDescription("Project description");
        project.setProjectOwner(user);
        projectService.addProject(project);
        Task task = new Task();
        task.setTaskName("Task Name");
        task.setDescription("Task description");
        task.setProject(project);
        taskService.addTask(task);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldCreateTaskWithAdminRole() throws Exception {

        Task task = new Task();
        task.setTaskName("Task Name");
        task.setDescription("Task description");
        task.setProject(project);
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateTaskIfNotAdmin() throws Exception {
        Task task = new Task();
        task.setTaskName("Task Name");
        task.setDescription("Task description");
        task.setProject(project);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllTasksWithAdminRole() throws Exception {

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetTaskByIdWithAdminRole() throws Exception {

        mockMvc.perform(get("/tasks/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateTaskIfAdmin() throws Exception {

        Task updatedTask = new Task();
        updatedTask.setTaskName("TestTask");
        updatedTask.setDescription("TestDescription");

        mockMvc.perform(put("/tasks/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteTaskWithAdminRole() throws Exception {

        mockMvc.perform(delete("/tasks/{id}", 1))
                .andExpect(status().isOk());
    }
}
