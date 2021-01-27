package com.wcreators.todo_api.common.servicies.user_from_auth;

import com.wcreators.todo_api.configs.security.details.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserFromAuthImpl implements UserFromAuth {
    @Override
    public CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
