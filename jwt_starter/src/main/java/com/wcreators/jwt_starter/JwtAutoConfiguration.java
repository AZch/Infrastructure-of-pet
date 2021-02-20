package com.wcreators.jwt_starter;

import com.wcreators.jwt_starter.services.details.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "enable.jwt", havingValue = "true")
@ConditionalOnBean(CustomUserDetailsService.class)
@ComponentScan
@Configuration
public class JwtAutoConfiguration {
}
