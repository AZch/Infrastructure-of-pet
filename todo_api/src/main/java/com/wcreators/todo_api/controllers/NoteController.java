package com.wcreators.todo_api.controllers;

import com.wcreators.todo_api.common.servicies.user_from_auth.UserFromAuth;
import com.wcreators.todo_api.configs.security.details.CustomUserDetails;
import com.wcreators.todo_api.constants.Routes;
import com.wcreators.todo_api.entities.Note;
import com.wcreators.todo_api.entities.User;
import com.wcreators.todo_api.exceptions.EntityNotFoundException;
import com.wcreators.todo_api.dto.NoteDto;
import com.wcreators.todo_api.repositories.NoteRepository;
import com.wcreators.todo_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Routes.Notes.BASE)
public class NoteController {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelAssembler assembler;

    @Autowired
    private CollectionAssembler collectionAssembler;

    @Autowired
    private UserFromAuth userFromAuth;

    @GetMapping
    public CollectionModel<EntityModel<Note>> all() {
        CustomUserDetails customUserDetails = this.userFromAuth.getUserDetails();
        List<EntityModel<Note>> notes = repository
                .findAllByUsersIdAndDeletedFalse(customUserDetails.getId())
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return collectionAssembler.toModel(notes);
    }

    @GetMapping(Routes.Notes.GET_ONE)
    public EntityModel<Note> one(@PathVariable Long id) {
        CustomUserDetails customUserDetails = this.userFromAuth.getUserDetails();
        Note note = repository
                .findByIdAndUsersIdAndDeletedFalse(id, customUserDetails.getId())
                .orElseThrow(() -> noteNotFoundById(id));
        return assembler.toModel(note);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Note>>  create(@RequestBody NoteDto noteDto) {
        CustomUserDetails customUserDetails = userFromAuth.getUserDetails();
        User user = userRepository
                .findById(customUserDetails.getId())
                .orElseThrow();
        Note note = repository.save(
                Note.builder()
                        .title(noteDto.getTitle())
                        .content(noteDto.getContent())
                        .build()
        );
        user.getNotes().add(note);
        userRepository.save(user);

        EntityModel<Note> entityModel = assembler.toModel(note);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping(Routes.Notes.EDIT)
    public ResponseEntity<EntityModel<Note>> editNote(@RequestBody NoteDto noteDto, @PathVariable Long id) {
        CustomUserDetails customUserDetails = userFromAuth.getUserDetails();
        Note updatedNote = repository
                .findByIdAndUsersIdAndDeletedFalse(id, customUserDetails.getId())
                .map(note -> {
                    note.setTitle(noteDto.getTitle());
                    note.setContent(noteDto.getContent());
                    return repository.save(note);
                }).orElseThrow(() -> noteNotFoundById(id));

        EntityModel<Note> entityModel = assembler.toModel(updatedNote);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping(Routes.Notes.DELETE)
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        CustomUserDetails customUserDetails = userFromAuth.getUserDetails();
        repository
                .findByIdAndUsersIdAndDeletedFalse(id, customUserDetails.getId())
                .map(note -> {
                    note.setDeleted(true);
                    return repository.save(note);
                }).orElseThrow(() -> noteNotFoundById(id));
        return ResponseEntity.noContent().build();
    }

    public static RuntimeException noteNotFoundById(Long id) {
        return new EntityNotFoundException("note", "id", id);
    }

}
