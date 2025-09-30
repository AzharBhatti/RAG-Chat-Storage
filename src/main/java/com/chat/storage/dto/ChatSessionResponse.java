package com.chat.storage.dto;

import com.chat.storage.entity.ChatSessionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ChatSessionResponse {
    private UUID id;
    private String userId;
    private String name;
    private boolean favorite;

    public ChatSessionResponse(ChatSessionEntity chatSession) {
        this.id = chatSession.getId();
        this.userId = chatSession.getUserId();
        this.name = chatSession.getName();
        this.favorite = chatSession.isFavorite();
    }
}
