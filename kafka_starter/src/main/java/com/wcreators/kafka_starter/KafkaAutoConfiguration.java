package com.wcreators.kafka_starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
@Configuration
@ComponentScan
public class KafkaAutoConfiguration {
}
