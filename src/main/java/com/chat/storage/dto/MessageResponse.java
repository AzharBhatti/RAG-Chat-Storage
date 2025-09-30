package com.chat.storage.dto;

import com.chat.storage.entity.MessageEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageResponse {
    private String sender;
    private String content;
    private String context;

    public MessageResponse(MessageEntity chatMessage) {
        this.sender = chatMessage.getSender();
        this.content = chatMessage.getContent();
        this.context = chatMessage.getContext();
    }
}
