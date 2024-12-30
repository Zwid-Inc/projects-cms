package com.projectscms.server.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {

    private String text;
    private Long senderId;
}
