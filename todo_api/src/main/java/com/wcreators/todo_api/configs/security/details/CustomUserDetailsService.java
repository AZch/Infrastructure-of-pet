package com.wcreators.todo_api.configs.security.details;

import com.wcreators.todo_api.exceptions.EntityNotFoundException;
import com.wcreators.todo_api.services.UserService;
import com.wcreators.todo_api.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByIdAndRole(Long id, String role) throws UsernameNotFoundException {
        User user = userService.findByIdAndRole(id, role).orElseThrow(() -> new EntityNotFoundException("User", "id", id));
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }
}
