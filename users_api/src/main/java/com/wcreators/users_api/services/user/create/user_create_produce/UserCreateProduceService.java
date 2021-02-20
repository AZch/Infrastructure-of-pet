package com.wcreators.users_api.services.user.create.user_create_produce;

import com.wcreators.users_api.services.user.models.UserCreatedModel;

public interface UserCreateProduceService {
    void produceUserCreatedEvent(UserCreatedModel userCreatedModelDTO);
}
