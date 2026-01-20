package com.pragma.challenge.msvc_users.application.handler;

import com.pragma.challenge.msvc_users.application.dto.request.UserRequest;
import com.pragma.challenge.msvc_users.application.dto.response.IsOwnerResponse;
import com.pragma.challenge.msvc_users.application.dto.response.UserResponse;

public interface UserHandler {
    UserResponse createOwner(UserRequest owner);
    IsOwnerResponse isOwner(Long id);
    //UserResponse createEmployee();
    UserResponse createCustomer(UserRequest client);
}
