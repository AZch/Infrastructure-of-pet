package com.wcreators.users_api.services.user.create.user_create_produce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcreators.kafka_starter.dto.RoleDTO;
import com.wcreators.kafka_starter.dto.UserCreatedDTO;
import com.wcreators.users_api.services.user.models.UserCreatedModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserCreateProduceKafkaService implements UserCreateProduceService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<Long, UserCreatedDTO> kafkaTemplate;

    @Override
    public void produceUserCreatedEvent(UserCreatedModel userCreatedDTO) {
        log.info("<= sending {}", writeValueAsString(userCreatedDTO));
        kafkaTemplate.send("server.starship", UserCreatedDTO.builder()
                .id(userCreatedDTO.getId())
                .role(RoleDTO.builder()
                        .id(userCreatedDTO.getRole().getId())
                        .name(userCreatedDTO.getRole().getName())
                        .build())
                .build());
    }

    private String writeValueAsString(UserCreatedModel dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Writing value to JSON failed: %s", dto.toString()));
        }
    }
}
