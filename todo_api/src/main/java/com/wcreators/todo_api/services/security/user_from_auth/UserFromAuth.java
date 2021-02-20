package com.wcreators.todo_api.services.security.user_from_auth;

import com.wcreators.todo_api.services.security.details.CustomUserDetails;

public interface UserFromAuth {
    CustomUserDetails getUserDetails();
}
