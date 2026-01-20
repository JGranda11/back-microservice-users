package com.pragma.challenge.msvc_users.domain.spi;

import com.pragma.challenge.msvc_users.domain.model.User;

public interface IUserPersistencePort {
    User saveUser(User user);
    User findByIdentityDocument(String identityDocument);
    User findByEmail(String email);

    User findById(Long id);
    void deletedById(Long id);
}
