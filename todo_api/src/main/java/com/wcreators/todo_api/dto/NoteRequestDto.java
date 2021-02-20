package com.wcreators.todo_api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class NoteRequestDto {
    String title;
    String content;
}
