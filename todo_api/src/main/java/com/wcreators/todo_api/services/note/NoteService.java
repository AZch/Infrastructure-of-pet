package com.wcreators.todo_api.services.note;

import com.wcreators.todo_api.entities.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<Note> allForUser(Long userId);

    Optional<Note> getByIdForUser(Long noteId, Long userId);

    Note save(Note note);
}
