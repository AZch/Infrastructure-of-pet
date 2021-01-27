package com.wcreators.todo_api.configs.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByIdAndRole(Long id, String role) throws UsernameNotFoundException;
}
