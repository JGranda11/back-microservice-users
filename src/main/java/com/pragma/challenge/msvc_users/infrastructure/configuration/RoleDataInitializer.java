package com.pragma.challenge.msvc_users.infrastructure.configuration;

import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RoleDataInitializer {
    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            if (roleRepository.count() > 0) {
                return;
            }

            roleRepository.saveAll(List.of(
                    RoleEntity.builder()
                            .name(RoleName.ADMIN)
                            .description("Administrator")
                            .build(),
                    RoleEntity.builder()
                            .name(RoleName.OWNER)
                            .description("Restaurant owner")
                            .build(),
                    RoleEntity.builder()
                            .name(RoleName.EMPLOYEE)
                            .description("Restaurant employee")
                            .build(),
                    RoleEntity.builder()
                            .name(RoleName.CUSTOMER)
                            .description("Customer")
                            .build()
            ));
        };
    }
}
