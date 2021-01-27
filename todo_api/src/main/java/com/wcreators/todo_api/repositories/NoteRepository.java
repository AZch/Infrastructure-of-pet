package com.wcreators.todo_api.repositories;

import com.wcreators.todo_api.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUsersIdAndDeletedFalse(Long id);

    Optional<Note> findByIdAndUsersIdAndDeletedFalse(Long id, Long userId);
}
