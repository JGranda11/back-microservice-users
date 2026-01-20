package com.pragma.challenge.msvc_users.infrastructure.output.jpa.mapper;

import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {
    UserEntity toEntity(User user);
    List<UserEntity> toEntities(List<User> userList);
    User toDomain(UserEntity entity);
    List<User> toDomains(List<UserEntity> entityList);
}
