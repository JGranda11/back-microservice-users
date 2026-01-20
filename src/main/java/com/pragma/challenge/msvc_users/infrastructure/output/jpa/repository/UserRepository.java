package com.pragma.challenge.msvc_users.infrastructure.output.jpa.repository;

import com.pragma.challenge.msvc_users.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByIdentityDocument(String identityDocument);

}
