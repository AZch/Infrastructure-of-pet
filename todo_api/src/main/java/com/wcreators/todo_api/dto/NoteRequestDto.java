package com.wcreators.todo_api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@Builder
public class NoteRequestDto {

    @NotEmpty
    @Size(min = 2)
    String title;

    @NotEmpty
    @Size(min = 2)
    String content;
}
