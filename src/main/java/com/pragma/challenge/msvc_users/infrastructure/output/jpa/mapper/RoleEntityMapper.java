package com.pragma.challenge.msvc_users.infrastructure.output.jpa.mapper;

import com.pragma.challenge.msvc_users.domain.model.Role;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {
    RoleEntity toEntity(Role role);
    List<RoleEntity> toEntities(List<Role> roleList);
    Role toDomain(RoleEntity roleEntity);
    List<Role> toDomains (List<RoleEntity> roleEntities);
}
