package com.pragma.challenge.msvc_users.application.mapper.response;

import com.pragma.challenge.msvc_users.application.dto.response.UserResponse;
import com.pragma.challenge.msvc_users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserResponseMapper {
    UserResponse toResponse(User user);
    List<UserResponse> toResponses(List<User> userList);
}
