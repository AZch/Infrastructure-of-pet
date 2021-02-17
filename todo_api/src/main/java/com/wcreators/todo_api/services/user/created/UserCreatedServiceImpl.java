package com.wcreators.todo_api.services.user.created;

import com.wcreators.kafka_starter.dto.UserCreatedDTO;
import com.wcreators.todo_api.entities.Role;
import com.wcreators.todo_api.entities.User;
import com.wcreators.todo_api.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCreatedServiceImpl implements UserCreatedService {

    private final UserRepository userRepository;

    public UserCreatedServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @KafkaListener(id = "Starship", topics = {"server.starship"}, containerFactory = "singleUserCreatedFactory")
    public void consumeUserCreated(UserCreatedDTO userCreatedDTO) {
        log.info("=> consumed {}", userCreatedDTO.toString());
        userRepository.save(User.builder()
                .id(userCreatedDTO.getId())
                .role(Role.builder()
                        .id(userCreatedDTO.getRole().getId())
                        .name(userCreatedDTO.getRole().getName())
                        .build())
                .build());
    }
}
