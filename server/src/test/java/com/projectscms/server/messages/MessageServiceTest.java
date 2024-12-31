package com.projectscms.server.messages;


import com.projectscms.server.users.User;
import com.projectscms.server.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class MessageServiceTest {

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MessageServiceImpl messageService;

    private Message sampleMessage;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("Jan");
        sampleUser.setLastName("Kowalski");
        sampleUser.setEmail("test@example.com");
        userRepository.save(sampleUser);
        sampleMessage = new Message();
        sampleMessage.setId(1L);
        sampleMessage.setSender(sampleUser);
        sampleMessage.setText("Test message");
    }

    @Test
    void testCreateMessage() {
        // Given
        when(messageRepository.save(sampleMessage)).thenReturn(sampleMessage);

        // When
        Message result = messageService.addMessage(sampleMessage);

        // Then
        assertEquals(result, sampleMessage);
        verify(messageRepository, times(1)).save(sampleMessage);
    }

    @Test
    void testGetAllMessages() {
        // Given
        List<Message> messageList = List.of(sampleMessage);
        Page<Message> messagePage = new PageImpl<>(messageList);
        Pageable pageable = PageRequest.of(0, 10);

        // When
        when(messageRepository.findAll(pageable)).thenReturn(messagePage);
        List<Message> messages = messageService.getMessages(pageable).getContent();

        // Then
        assertNotNull(messages);
        assertEquals(1, messages.size());
        verify(messageRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetMessageById() {
        // Given
        when(messageRepository.findById(1L)).thenReturn(Optional.of(sampleMessage));

        // When
        Optional<Message> foundMessage = messageService.getMessageById(1L);

        // Then
        assertTrue(foundMessage.isPresent());
        assertEquals("Test message", foundMessage.get().getText());
        verify(messageRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteMessageById_MessageExists() {
        // Given
        when(messageRepository.findById(1L)).thenReturn(Optional.of(sampleMessage));

        // When
        messageService.deleteMessageById(1L);

        // Then
        verify(messageRepository, times(1)).deleteById(sampleMessage.getId());
    }

    @Test
    void testDeleteMessageById_MessageNotExists() {
        // Given
        when(messageRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        messageService.deleteMessageById(999L);

        // Then
        verify(messageRepository, never()).delete(any(Message.class));
    }
}
