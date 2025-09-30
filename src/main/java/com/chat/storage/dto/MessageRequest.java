package com.chat.storage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class MessageRequest {

    @NotNull(message = "Id can not be null")
    private UUID id;
    @NotBlank(message = "Sender can not be null or empty")
    private String  sender;
    @NotBlank(message = "Content can not be null or empty")
    private String  content;
    private String  context;
}
