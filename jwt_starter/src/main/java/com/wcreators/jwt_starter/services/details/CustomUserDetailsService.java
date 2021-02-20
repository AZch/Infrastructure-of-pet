package com.wcreators.jwt_starter.services.details;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
    UserDetails loadUserByIdAndRole(Long id, String role);
}
