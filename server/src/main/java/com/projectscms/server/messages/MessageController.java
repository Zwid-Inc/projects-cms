package com.projectscms.server.messages;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Message> getAllMessages(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageService.getMessages(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getMessageById(@PathVariable Long id){
        return messageService.getMessageById(id).isPresent() ?
                ResponseEntity.ok(messageService.getMessageById(id).get()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addMessage(@RequestBody Message message) {
        try{
            Message createdMessage = messageService.addMessage(message);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{messageId}").buildAndExpand(createdMessage.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e){
            LOG.error(">>>addMessage<<< ERROR: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMessageById(@PathVariable Long id){
        return messageService.getMessageById(id).map(message -> {
            messageService.deleteMessageById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
