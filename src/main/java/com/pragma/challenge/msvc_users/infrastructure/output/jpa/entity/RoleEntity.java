package com.pragma.challenge.msvc_users.infrastructure.output.jpa.entity;

import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="name", nullable = false, unique = true)
    private RoleName name;

    @Column(name = "description", nullable = false)
    private String description;
}
