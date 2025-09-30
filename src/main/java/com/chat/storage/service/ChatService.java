package com.chat.storage.service;

import com.chat.storage.dto.ChatSessionRequest;
import com.chat.storage.dto.ChatSessionResponse;
import com.chat.storage.dto.MessageRequest;
import com.chat.storage.dto.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    ChatSessionResponse createSession(ChatSessionRequest request);

    List<ChatSessionResponse> getSessions(String userId, boolean favoritesOnly);

    ChatSessionResponse renameSession(UUID id, String newName);
    ChatSessionResponse toggleFavorite(UUID messageId);
    MessageResponse addMessageToSession(MessageRequest messageRequest);
    List<MessageResponse> getMessagesBySession(UUID sessionId);

    void deleteSession(UUID id);
}
