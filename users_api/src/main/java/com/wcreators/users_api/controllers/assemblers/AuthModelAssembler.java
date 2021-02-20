package com.wcreators.users_api.controllers.assemblers;

import com.wcreators.users_api.dto.AuthResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AuthModelAssembler implements RepresentationModelAssembler<AuthResponseDto, EntityModel<AuthResponseDto>> {
    @Override
    public EntityModel<AuthResponseDto> toModel(AuthResponseDto entity) {
        return EntityModel.of(entity);
    }
}
