package com.projectscms.server.messages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MessageService {

    Page<Message> getMessages(Pageable pageable);
    Optional<Message> getMessageById(Long id);
    Page<Message> getUserMessages(Long userId, Pageable pageable);
    Message addMessage(Message message);
    void deleteMessageById(Long id);
}
