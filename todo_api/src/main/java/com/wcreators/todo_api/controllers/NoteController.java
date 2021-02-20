package com.wcreators.todo_api.controllers;

import com.wcreators.todo_api.services.security.user_from_auth.UserFromAuth;
import com.wcreators.todo_api.services.security.details.CustomUserDetails;
import com.wcreators.todo_api.constants.Routes;
import com.wcreators.todo_api.controllers.assemblers.CollectionAssembler;
import com.wcreators.todo_api.controllers.assemblers.ModelAssembler;
import com.wcreators.todo_api.controllers.mappers.NoteMapper;
import com.wcreators.todo_api.dto.NoteRequestDto;
import com.wcreators.todo_api.dto.NoteResponseDto;
import com.wcreators.todo_api.exceptions.EntityNotFoundException;
import com.wcreators.todo_api.services.note.NoteService;
import com.wcreators.todo_api.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(Routes.Notes.BASE)
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;
    private final ModelAssembler assembler;
    private final CollectionAssembler collectionAssembler;
    private final UserFromAuth userFromAuth;
    private final NoteMapper mapper;

    @GetMapping
    public CollectionModel<EntityModel<NoteResponseDto>> all() {
        CustomUserDetails customUserDetails = this.userFromAuth.getUserDetails();
        val notes = noteService
                .allForUser(customUserDetails.getId())
                .stream()
                .map(mapper::toDto)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return collectionAssembler.toModel(notes);
    }

    @GetMapping(Routes.Notes.GET_ONE)
    public EntityModel<NoteResponseDto> one(@PathVariable Long id) {
        CustomUserDetails customUserDetails = this.userFromAuth.getUserDetails();
        val note = noteService
                .getByIdForUser(id, customUserDetails.getId())
                .orElseThrow(() -> new EntityNotFoundException("note", "id", id));
        return assembler.toModel(mapper.toDto(note));
    }

    @PostMapping
    public ResponseEntity<EntityModel<NoteResponseDto>>  create(@RequestBody NoteRequestDto noteDto) {
        CustomUserDetails customUserDetails = userFromAuth.getUserDetails();
        val user = userService
                .getById(customUserDetails.getId())
                .orElseThrow();
        val note = noteService.save(mapper.toDomain(noteDto));
        user.getNotes().add(note);
        userService.save(user);

        EntityModel<NoteResponseDto> entityModel = assembler.toModel(mapper.toDto(note));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping(Routes.Notes.EDIT)
    public ResponseEntity<EntityModel<NoteResponseDto>> editNote(@RequestBody NoteRequestDto noteDto, @PathVariable Long id) {
        val customUserDetails = userFromAuth.getUserDetails();
        val updatedNote = noteService
                .getByIdForUser(id, customUserDetails.getId())
                .map(note -> noteService.save(mapper.replaceFromDto(note, noteDto)))
                .orElseThrow(() -> new EntityNotFoundException("note", "id", id));

        val entityModel = assembler.toModel(mapper.toDto(updatedNote));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(Routes.Notes.DELETE)
    public void deleteNote(@PathVariable Long id) {
        val customUserDetails = userFromAuth.getUserDetails();
        noteService
                .getByIdForUser(id, customUserDetails.getId())
                .map(note -> noteService.save(mapper.markDeleted(note)))
                .orElseThrow(() -> new EntityNotFoundException("note", "id", id));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage());
    }
}
