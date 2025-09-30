package com.chat.storage.repository;

import com.chat.storage.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    Page<MessageEntity> findBySessionIdOrderByCreatedAtAsc(UUID sessionId, Pageable pageable);
}
