package com.wcreators.kafka_starter.services.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
@Service
public class DefaultConsumer implements Consumer {

    private final String kafkaServer;
    private final String kafkaGroupId;

    public DefaultConsumer(
            @Value("${spring.kafka.bootstrap-servers}") String kafkaServer,
            @Value("${spring.kafka.consumer.group-id}") String kafkaGroupId
    ) {
        this.kafkaGroupId = kafkaGroupId;
        this.kafkaServer = kafkaServer;
    }

    @Override
    public Map<String, Object> getConfig() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true
        );
    }
}
