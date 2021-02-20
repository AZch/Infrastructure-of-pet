package com.wcreators.todo_api.controllers.mappers;

import com.wcreators.todo_api.dto.NoteRequestDto;
import com.wcreators.todo_api.dto.NoteResponseDto;
import com.wcreators.todo_api.entities.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public Note toDomain(NoteRequestDto noteDto) {
        return Note.builder()
                .content(noteDto.getContent())
                .title(noteDto.getTitle())
                .build();
    }

    public NoteResponseDto toDto(Note note) {
        return NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }

    public Note replaceFromDto(Note note, NoteRequestDto noteDto) {
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        return note;
    }

    public Note markDeleted(Note note) {
        note.setDeleted(true);
        return note;
    }
}
