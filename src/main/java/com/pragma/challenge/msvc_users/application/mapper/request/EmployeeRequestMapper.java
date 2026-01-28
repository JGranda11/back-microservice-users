package com.pragma.challenge.msvc_users.application.mapper.request;

import com.pragma.challenge.msvc_users.application.dto.request.EmployeeRequest;
import com.pragma.challenge.msvc_users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeRequestMapper {
    User toDomain(EmployeeRequest employeeRequest);
    List<User> toDomains(List<EmployeeRequest> requestList);
}
