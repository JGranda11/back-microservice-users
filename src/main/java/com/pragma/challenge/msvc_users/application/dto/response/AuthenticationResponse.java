package com.pragma.challenge.msvc_users.application.dto.response;

import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private RoleName role;
    private Long id;
}
