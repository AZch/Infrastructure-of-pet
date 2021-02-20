package com.wcreators.todo_api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NoteResponseDto {
    Long id;
    String title;
    String content;
}
