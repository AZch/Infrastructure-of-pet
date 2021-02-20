package com.wcreators.todo_api.controllers.assemblers;

import com.wcreators.todo_api.controllers.NoteController;
import com.wcreators.todo_api.dto.NoteRequestDto;
import com.wcreators.todo_api.dto.NoteResponseDto;
import com.wcreators.todo_api.entities.Note;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ModelAssembler implements RepresentationModelAssembler<NoteResponseDto, EntityModel<NoteResponseDto>> {

    @Override
    public EntityModel<NoteResponseDto> toModel(NoteResponseDto entity) {
        return EntityModel.of(
                entity,
                WebMvcLinkBuilder.linkTo(methodOn(NoteController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(NoteController.class).all()).withRel("notes")
        );
    }
}
