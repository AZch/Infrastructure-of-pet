package com.wcreators.todo_api.services.note;

import com.wcreators.todo_api.entities.Note;
import com.wcreators.todo_api.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceByRepository implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> allForUser(Long userId) {
        return noteRepository.findAllByUsersIdAndDeletedFalse(userId);
    }

    @Override
    public Optional<Note> getByIdForUser(Long noteId, Long userId) {
        return noteRepository.findByIdAndUsersIdAndDeletedFalse(noteId, userId);
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }
}
