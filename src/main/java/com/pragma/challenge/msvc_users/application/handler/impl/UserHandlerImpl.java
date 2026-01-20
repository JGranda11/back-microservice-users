package com.pragma.challenge.msvc_users.application.handler.impl;

import com.pragma.challenge.msvc_users.application.dto.request.UserRequest;
import com.pragma.challenge.msvc_users.application.dto.response.IsOwnerResponse;
import com.pragma.challenge.msvc_users.application.dto.response.UserResponse;
import com.pragma.challenge.msvc_users.application.handler.UserHandler;
import com.pragma.challenge.msvc_users.application.mapper.request.UserRequestMapper;
import com.pragma.challenge.msvc_users.application.mapper.response.UserResponseMapper;
import com.pragma.challenge.msvc_users.domain.api.IUserServicePort;
import com.pragma.challenge.msvc_users.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandlerImpl implements UserHandler {
    private final IUserServicePort userServicePort;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse createOwner(UserRequest owner) {
        User user = encryptPassword(owner);
        return userResponseMapper.toResponse(
                userServicePort.createOwner(user)
        );
    }

    @Override
    public IsOwnerResponse isOwner(Long id) {
        return IsOwnerResponse.builder()
                .isOwner(userServicePort.isOwner(id))
                .build();
    }

    @Override
    public UserResponse createCustomer(UserRequest client) {
        User user = encryptPassword(client);
        return userResponseMapper.toResponse(userServicePort.createCustomer(user));
    }

    private User encryptPassword(UserRequest request){
        User user = userRequestMapper.toDomain(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }
}
