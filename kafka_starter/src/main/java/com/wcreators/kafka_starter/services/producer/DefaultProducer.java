package com.wcreators.kafka_starter.services.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DefaultProducer implements Producer {

    private final String kafkaServer;
    private final String kafkaProducerId;

    public DefaultProducer(
            @Value("${spring.kafka.bootstrap-servers}") String kafkaServer,
            @Value("${spring.kafka.producer.client-id}") String kafkaProducerId
    ) {
        this.kafkaServer = kafkaServer;
        this.kafkaProducerId = kafkaProducerId;
    }

    @Override
    public Map<String, Object> getConfig() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId
        );
    }
}
