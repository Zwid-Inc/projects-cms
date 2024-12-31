package com.projectscms.server.users;


import com.projectscms.server.projects.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private UserServiceImpl userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("Jan");
        sampleUser.setLastName("Kowalski");
        sampleUser.setEmail("test@example.com");
    }

    @Test
    void testCreateUser() {
        // Given
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        // When
        boolean result = userService.createUser(sampleUser);

        // Then
        assertTrue(result);
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    void testGetAllUsers() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        // When
        Optional<User> foundUser = userService.getUserById(1L);

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("Jan", foundUser.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByEmail() {
        // Given
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(sampleUser));

        // When
        Optional<User> foundUser = userService.getUserByEmail(email);

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testUpdateUser_UserExists() {
        // Given
        User updated = new User();
        updated.setName("Adam");
        updated.setLastName("Nowak");
        updated.setEmail("adam.nowak@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        // When
        boolean updatedResult = userService.updateUser(1L, updated);

        // Then
        assertTrue(updatedResult);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserNotExists() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        boolean updatedResult = userService.updateUser(999L, sampleUser);

        // Then
        assertFalse(updatedResult);
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUserById_UserExists() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(projectRepository.findByMaintainersContains(1L)).thenReturn(List.of());

        // When
        boolean result = userService.deleteUserById(1L);

        // Then
        assertTrue(result);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(sampleUser);
    }

    @Test
    void testDeleteUserById_UserNotExists() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        boolean result = userService.deleteUserById(999L);

        // Then
        assertFalse(result);
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).delete(any(User.class));
    }
}
