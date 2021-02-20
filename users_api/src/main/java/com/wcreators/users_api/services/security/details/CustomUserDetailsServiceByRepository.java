package com.wcreators.users_api.services.security.details;

import com.wcreators.users_api.entities.User;
import com.wcreators.users_api.exceptions.EntityNotFoundException;
import com.wcreators.users_api.services.user.UserService;
import lombok.RequiredArgsConstructor;
import com.wcreators.jwt_starter.services.details.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsServiceByRepository implements CustomUserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByIdAndRole(Long id, String role) throws UsernameNotFoundException {
        User user = userService.getByIdAndRole(id, role).orElseThrow(() -> new EntityNotFoundException("User", "id", id));
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }
}
