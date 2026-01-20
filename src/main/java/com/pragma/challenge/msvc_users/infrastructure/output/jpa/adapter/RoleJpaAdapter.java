package com.pragma.challenge.msvc_users.infrastructure.output.jpa.adapter;

import com.pragma.challenge.msvc_users.domain.model.Role;
import com.pragma.challenge.msvc_users.domain.spi.IRolePersistencePort;
import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    public Role findByName(RoleName role) {
        return roleEntityMapper.toDomain(
          roleRepository.findByName(role).orElse(null)
        );
    }
}
