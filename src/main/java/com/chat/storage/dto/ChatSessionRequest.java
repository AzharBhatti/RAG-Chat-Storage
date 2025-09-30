package com.chat.storage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ChatSessionRequest {

    @NotBlank(message = "User id can not be null")
    private String userId;

    private UUID id;

    private boolean isFavorite;

    @NotBlank(message = "Name can not be null")
    private String name;
}
