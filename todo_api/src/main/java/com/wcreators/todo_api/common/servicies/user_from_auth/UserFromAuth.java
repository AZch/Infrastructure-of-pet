package com.wcreators.todo_api.common.servicies.user_from_auth;

import com.wcreators.todo_api.configs.security.details.CustomUserDetails;

public interface UserFromAuth {
    CustomUserDetails getUserDetails();
}
