package com.wcreators.users_api.services.user.create;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcreators.kafka_starter.dto.RoleDTO;
import com.wcreators.users_api.constants.Roles;
import com.wcreators.users_api.dto.RegistrationRequestDTO;
import com.wcreators.users_api.entities.Role;
import com.wcreators.users_api.entities.User;
import com.wcreators.users_api.dto.UserCreatedModelDTO;
import com.wcreators.users_api.repositories.RoleRepository;
import com.wcreators.users_api.repositories.UserRepository;
import com.wcreators.kafka_starter.dto.UserCreatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserCreateServiceImpl implements UserCreateService {

    private final KafkaTemplate<Long, UserCreatedDTO> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreateServiceImpl(
            KafkaTemplate<Long, UserCreatedDTO> kafkaTemplate,
            ObjectMapper objectMapper,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> saveUser(RegistrationRequestDTO registrationDTO) {
        Role role = roleRepository
                .findByName(Roles.USER.getName())
                .orElseGet(() -> roleRepository.save(Role.builder().name(Roles.USER.getName()).build()));
        Optional<User> isUserExist = userRepository.findByUsername(registrationDTO.getUsername());
        if (isUserExist.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(
                userRepository.save(
                        User.builder()
                                .username(registrationDTO.getUsername())
                                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                                .role(role)
                                .build()
                )
        );
    }

    @Override
    public void produceUserCreated(UserCreatedModelDTO userCreatedDTO) {
        log.info("<= sending {}", writeValueAsString(userCreatedDTO));
        kafkaTemplate.send("server.starship", UserCreatedDTO.builder()
                .id(userCreatedDTO.getId())
                .role(RoleDTO.builder()
                        .id(userCreatedDTO.getRole().getId())
                        .name(userCreatedDTO.getRole().getName())
                        .build())
                .build());
    }

    private String writeValueAsString(UserCreatedModelDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Writing value to JSON failed: %s", dto.toString()));
        }
    }
}
