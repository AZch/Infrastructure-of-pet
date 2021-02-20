package com.wcreators.todo_api.controllers.assemblers;

import com.wcreators.todo_api.controllers.NoteController;
import com.wcreators.todo_api.controllers.mappers.NoteMapper;
import com.wcreators.todo_api.dto.NoteResponseDto;
import com.wcreators.todo_api.entities.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CollectionAssembler implements RepresentationModelAssembler<List<EntityModel<NoteResponseDto>>, CollectionModel<EntityModel<NoteResponseDto>>> {

    @Override
    public CollectionModel<EntityModel<NoteResponseDto>> toModel(List<EntityModel<NoteResponseDto>> entity) {
        return CollectionModel.of(
                entity,
                WebMvcLinkBuilder.linkTo(methodOn(NoteController.class).all()).withSelfRel()
        );
    }
}
