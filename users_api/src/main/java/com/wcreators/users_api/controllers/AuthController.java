package com.wcreators.users_api.controllers;

import com.wcreators.jwt_starter.services.jwt.JwtProvider;
import com.wcreators.users_api.constants.Errors;
import com.wcreators.users_api.constants.Routes;
import com.wcreators.users_api.controllers.assemblers.AuthModelAssembler;
import com.wcreators.users_api.controllers.mappers.UserMapper;
import com.wcreators.users_api.dto.AuthRequestDto;
import com.wcreators.users_api.dto.AuthResponseDto;
import com.wcreators.users_api.dto.RegistrationRequestDto;
import com.wcreators.users_api.exceptions.BadRequestException;
import com.wcreators.users_api.exceptions.EntityNotFoundException;
import com.wcreators.users_api.services.user.UserService;
import com.wcreators.users_api.services.user.create.user_create.UserCreateService;
import com.wcreators.users_api.services.user.create.user_create_produce.UserCreateProduceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(Routes.Auth.BASE)
public class AuthController {

    private final UserCreateService userCreateService;
    private final UserService userService;
    private final UserCreateProduceService userCreateProduceService;
    private final JwtProvider jwtProvider;
    private final UserMapper mapper;
    private final AuthModelAssembler assembler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(Routes.Auth.SIGNUP)
    public void signup(@RequestBody @Valid RegistrationRequestDto registrationRequestDTO) {
        val user = userCreateService.saveUser(mapper.dtoToRegistrationModel(registrationRequestDTO));
        userCreateProduceService.produceUserCreatedEvent(mapper.userToCreatedModel(user));
    }

    @PostMapping(Routes.Auth.SIGN_IN)
    public EntityModel<AuthResponseDto> signIn(@RequestBody @Valid AuthRequestDto body) throws BadRequestException {
        val user = userService.findByUsernameAndPassword(body.getUsername(), body.getPassword())
                .orElseThrow(() -> new BadRequestException(Errors.AuthError.USERNAME_OR_PASSWORD_INCORRECT));
        val jwtUser = mapper.userToJwtUser(user);
        val token = jwtProvider.generateToken(jwtUser);
        val authResponseDTO = AuthResponseDto.builder().token(token).build();
        return assembler.toModel(authResponseDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequestExceptionHandler(BadRequestException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFoundExceptionHandler(EntityNotFoundException e) {
        log.error(e.getMessage());
    }
}
