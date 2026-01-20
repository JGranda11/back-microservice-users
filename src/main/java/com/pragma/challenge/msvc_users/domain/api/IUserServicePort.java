package com.pragma.challenge.msvc_users.domain.api;

import com.pragma.challenge.msvc_users.domain.model.User;

public interface IUserServicePort {
    User createOwner(User user);
    User findById(Long id);
    User createEmployee(User user, Long restaurantId);
    User createCustomer(User user);
    boolean isOwner(Long userId);
}
