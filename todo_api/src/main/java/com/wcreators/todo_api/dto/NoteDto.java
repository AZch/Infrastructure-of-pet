package com.wcreators.todo_api.dto;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPOJOBuilder(withPrefix = "")
public class NoteDto {
    private String title;

    private String content;
}
