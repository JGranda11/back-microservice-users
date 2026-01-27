package com.pragma.challenge.msvc_users.application.mapper.response;

import com.pragma.challenge.msvc_users.application.dto.response.AuthenticationResponse;
import com.pragma.challenge.msvc_users.domain.model.auth.AuthenticatedUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthenticationResponseMapper {
    AuthenticationResponse toResponse(AuthenticatedUser user);
    List<AuthenticationResponse> toResponse(List<AuthenticatedUser> users);
}
