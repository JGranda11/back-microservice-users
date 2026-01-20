package com.pragma.challenge.msvc_users.infrastructure.output.jpa.repository;

import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName name);
}
