package com.wcreators.kafka_starter.configs.user_created;

import com.wcreators.kafka_starter.dto.UserCreatedDTO;
import com.wcreators.kafka_starter.services.producer.Producer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@ConditionalOnProperty(name = "messaging-topics.user-created", havingValue = "true")
@Configuration
public class UserCreatedProducerConfig {

    private final Producer producer;

    public UserCreatedProducerConfig(Producer producer) {
        this.producer = producer;
    }

    @Bean
    public KafkaTemplate<Long, UserCreatedDTO> kafkaUserCreatedTemplate() {
        KafkaTemplate<Long, UserCreatedDTO> template = new KafkaTemplate<>(producerUserCreatedFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<Long, UserCreatedDTO> producerUserCreatedFactory() {
        return new DefaultKafkaProducerFactory<>(producer.getConfig());
    }
}
