package com.wcreators.kafka_starter.configs.user_created;

import com.wcreators.kafka_starter.dto.UserCreatedDTO;
import com.wcreators.kafka_starter.services.consumer.Consumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@ConditionalOnProperty(name = "messaging-topics.user-created", havingValue = "true")
@Configuration
public class UserCreatedConsumerConfig {

    private final Consumer consumer;

    public UserCreatedConsumerConfig(Consumer consumer) {
        this.consumer = consumer;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleUserCreatedFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, UserCreatedDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerUserCreatedFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, UserCreatedDTO> consumerUserCreatedFactory() {
        return new DefaultKafkaConsumerFactory<>(consumer.getConfig());
    }
}
