package com.chat.storage.repository;

import com.chat.storage.entity.ChatSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSessionEntity, UUID> {
    List<ChatSessionEntity> findByUserId(String userId);
    List<ChatSessionEntity> findByUserIdAndFavorite(String userId, boolean favorite);

}
