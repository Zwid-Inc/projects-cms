package com.projectscms.server.users;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("TestName");
        user.setLastName("TestLastName");
        user.setRoles(Set.of(Role.USER));
        userService.createUser(user);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldCreateUserWithAdminRole() throws Exception {

        User user = new User();
        user.setEmail("testAdd@example.com");
        user.setPassword("password");
        user.setName("Test Name");
        user.setLastName("Test LastName");
        user.setRoles(Set.of(Role.USER));
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateUserIfNotAdmin() throws Exception {
        User user = new User();
        user.setEmail("test2@example.com");
        user.setPassword("password2");
        user.setName("TestName2");
        user.setLastName("TestLastName2");
        user.setRoles(Set.of(Role.USER));
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllUsersWithAdminRole() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserByIdWithAdminRole() throws Exception {
        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserByEmailWithAdminRole() throws Exception {
        mockMvc.perform(get("/users/email")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateUserIfAdmin() throws Exception {

        User updatedUser = new User();
        updatedUser.setEmail("updated@example.com");
        updatedUser.setName("Jakub");
        updatedUser.setLastName("Kowalski");
        updatedUser.setPassword("password");
        updatedUser.setRoles(Set.of(Role.USER));
        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUserWithAdminRole() throws Exception {

        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk());
    }
}
