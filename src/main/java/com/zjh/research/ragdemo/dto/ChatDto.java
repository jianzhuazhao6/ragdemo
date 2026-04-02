package com.zjh.research.ragdemo.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatDto {

    @NotNull( message = "User prompt cannot be empty")
    String userPrompt;
    String systemPrompt;
    String answer;
}
