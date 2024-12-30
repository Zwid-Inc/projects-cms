package com.projectscms.server.chat;


import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@AllArgsConstructor
public class ChatController {

  //  private final MessageService messageService;
    //private final UserService userService;


    @MessageMapping("/chat.sendMessage") // endpoint do wysyłania wiadomości
    @SendTo("/topic/public") // endpoint na który zostanie wysłana wiadomość - tam sie nasluchuje
   // @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public MessageRequest sendMessage(@Payload MessageRequest messageRequest) {

    /*    Optional<User> user = userService.getUserById(messageRequest.getSenderId());
         if(user.isPresent()){
               Message m = new Message();
              m.setText(messageRequest.getText());
             m.setSender(user.get());
             messageService.addMessage(m);
         }*/

       // trafia do wszystkich subskrybentów /topic/public
        return messageRequest;
    }
}
