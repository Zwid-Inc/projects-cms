package com.projectscms.server.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    private ObjectMapper objectMapper;

    private User user;
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
        Message message = new Message();
        message.setSender(user);
        message.setText("Test message");
        messageService.addMessage(message);
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void shouldCreateMessageWithUserRole() throws Exception {

        Message message = new Message();
        message.setSender(user);
        message.setText("Test message");

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isCreated());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllMessagesWithAdminRole() throws Exception {
        mockMvc.perform(get("/messages"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetMessageByIdWithAdminRole() throws Exception {
        mockMvc.perform(get("/messages/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteMessageWithAdminRole() throws Exception {

        mockMvc.perform(delete("/messages/{id}", 1))
                .andExpect(status().isOk());
    }
}
