package com.projectscms.server.messages;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Page<Message> getMessages(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public Page<Message> getUserMessages(Long userId, Pageable pageable) {
        return messageRepository.findBySenderId(userId,pageable);
    }

    @Override
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }


    @Override
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    @Transactional
    @Override
    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }
}
