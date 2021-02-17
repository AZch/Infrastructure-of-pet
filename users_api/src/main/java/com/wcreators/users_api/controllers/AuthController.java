package com.wcreators.users_api.controllers;

import com.wcreators.users_api.constants.Errors;
import com.wcreators.users_api.constants.Routes;
import com.wcreators.users_api.dto.AuthRequestDTO;
import com.wcreators.users_api.dto.AuthResponseDTO;
import com.wcreators.users_api.dto.RegistrationRequestDTO;
import com.wcreators.users_api.dto.UserCreatedModelDTO;
import com.wcreators.users_api.entities.User;
import com.wcreators.users_api.exceptions.BadRequestException;
import com.wcreators.users_api.services.security.jwt.JwtProvider;
import com.wcreators.users_api.services.user.create.UserCreateService;
import com.wcreators.users_api.services.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(Routes.Auth.BASE)
public class AuthController {

    private final UserCreateService userCreateService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(
            UserCreateService userCreateService,
            UserService userService,
            JwtProvider jwtProvider
    ) {
        this.userCreateService = userCreateService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(Routes.Auth.SIGNUP)
    public String signup(@RequestBody @Valid RegistrationRequestDTO body) {
        Optional<User> userOptional = userCreateService.saveUser(body);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(Errors.AuthError.USERNAME_ALREADY_IN_USE);
        }
        userCreateService.produceUserCreated(
                UserCreatedModelDTO.builder()
                        .id(userOptional.get().getId())
                        .role(userOptional.get().getRole())
                        .build()
        );
        return "OK";
    }

    @PostMapping(Routes.Auth.SIGN_IN)
    public AuthResponseDTO signIn(@RequestBody @Valid AuthRequestDTO body) throws BadRequestException {
        Optional<User> user = userService.findByUsernameAndPassword(body.getUsername(), body.getPassword());
        if (user.isEmpty()) {
            throw new BadRequestException(Errors.AuthError.USERNAME_OR_PASSWORD_INCORRECT);
        }
        String token = jwtProvider.generateToken(user.get());
        return AuthResponseDTO.builder().token(token).build();
    }

    @GetMapping(Routes.Auth.TEST)
    public boolean test() {
        return true;
    }
}
